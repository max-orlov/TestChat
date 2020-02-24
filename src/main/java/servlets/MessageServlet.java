package servlets;

import com.google.gson.Gson;
import dataBase.MessageDao;
import dataBase.UserDao;
import model.Message;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet(value = "/message", asyncSupported = true)
public class MessageServlet extends HttpServlet {
    private Map<String, AsyncContext> asyncContextMap = new ConcurrentHashMap<>();
    private BlockingQueue<Message> messageBlockingQueue = new LinkedBlockingQueue<>();
    private boolean run;

    private static Logger logger = Logger.getLogger(MessageServlet.class);

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (run){
                try {
                    Message message = messageBlockingQueue.take();

                    for(AsyncContext asyncContext : asyncContextMap.values()){
                        try {
                            sendMessage(asyncContext.getResponse().getWriter(), message);
                        } catch (IOException e) {
                            asyncContextMap.values().remove(asyncContext);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getHeader("Accept").equals("text/event-stream")){

            resp.setContentType("text/event-stream");
            resp.setHeader("Connection", "keep-alive");
            resp.setCharacterEncoding("UTF-8");

            String id = UUID.randomUUID().toString();
            AsyncContext asyncContext = req.startAsync();
            asyncContext.setTimeout(15000);
            asyncContext.addListener(new AsyncListener() {
                @Override
                public void onComplete(AsyncEvent asyncEvent) throws IOException {
                    asyncContextMap.remove(id);
                    System.out.println("Finish");
                }

                @Override
                public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                    asyncContextMap.remove(id);
                    System.out.println("Timeout");
                }

                @Override
                public void onError(AsyncEvent asyncEvent) throws IOException {
                    asyncContextMap.remove(id);
                    System.out.println("Error");
                }

                @Override
                public void onStartAsync(AsyncEvent asyncEvent) throws IOException {}
            });

            asyncContextMap.put(id, asyncContext);
            System.out.println(asyncContextMap);
        }
        else {
            resp.setContentType("application/json;charset=UTF8");
            List messages = null;
            try {
                messages = MessageDao.getAllMessages();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Collections.reverse(messages);
            resp.getWriter().write(new Gson().toJson(messages));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF8");
        if(req.getParameter("text") != null && req.getParameter("userId") != null) {
            Long userId = Long.parseLong(req.getParameter("userId"));
            User user = null;
            try {
                user = UserDao.getById(userId);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
            String text = req.getParameter("text");
            Date date = new Date();
            Message message = new Message(date, text, user);
            try {
                MessageDao.addMessage(message);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
            resp.getWriter().write(message.toString());
            messageBlockingQueue.add(message);
        }
    }

    private void sendMessage(PrintWriter writer, Message message){
        writer.println("data: " + message);
        writer.println();
        writer.flush();
    }

    @Override
    public void init() throws ServletException {
        run = true;
        thread.start();
    }

    @Override
    public void destroy() {
        run = false;
        asyncContextMap.clear();
        messageBlockingQueue.clear();
    }
}
