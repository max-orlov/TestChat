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

@WebServlet("/exit")
public class ExitServlet extends HttpServlet {

    private Set<User> usersOnline = UsersOnline.getUsersOnline();
    private Logger logger = Logger.getLogger(ExitServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                try {
                    User user = UserDao.getById(Long.valueOf(cookie.getValue()));
                    usersOnline.remove(user);
                    logger.info("User " + "'" + user.getName() + "'" + " leave chat");
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                cookie.setValue("");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
            if (cookie.getName().equals("userName")) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }
        resp.sendRedirect("index.html");
    }
}
