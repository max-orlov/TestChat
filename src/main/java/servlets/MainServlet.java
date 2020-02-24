package servlets;

import dataBase.UserDao;
import model.User;
import model.UsersOnline;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    private Set<User> usersOnline = UsersOnline.getUsersOnline();
    private Logger logger = Logger.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF8");
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("userId")) {
                    try {
                        User user = UserDao.getById(Long.valueOf(cookies[i].getValue()));
                        usersOnline.add(user);
                        logger.info("User " + "'" + user.getName() + "'" +  " have been connected");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    resp.sendRedirect("chat.html");
                    return;
                }
            }
            resp.sendRedirect("login.html");
        }
        else{
            resp.sendRedirect("login.html");
        }
    }
}
