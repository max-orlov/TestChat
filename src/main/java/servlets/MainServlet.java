package servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(MainServlet.class);
    private Map<String, String> usersOnline = UsersOnlineServlet.getUsersOnline();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF8");
        Cookie[] cookies = req.getCookies();
        HttpSession session = req.getSession(true);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userName")) {
                    if (usersOnline.containsValue(cookie.getValue())) {
                        usersOnline.replace(session.getId(), cookie.getValue());
                    } else {
                        usersOnline.put(session.getId(), cookie.getValue());
                    }
                    logger.info("User '" + cookie.getValue() + "' have been connected again");
                    logger.info(usersOnline.values());
                    resp.sendRedirect("chat.html");
                    return;
                }
            }
            resp.sendRedirect("login.html");
        } else {
            resp.sendRedirect("login.html");
        }
    }
}
