package servlets;

import dataBase.UserDao;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.SessionCookieConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(AuthenticationServlet.class);
    private Map<String, String> usersOnline = UsersOnlineServlet.getUsersOnline();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        User user = null;
        try {
            user = UserDao.getByName(name);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if(user == null) {
            resp.getWriter().write("user doesn't exists");
            logger.info("User not found");
            return;
        }
        if(user.getPassword().equals(password)) {
            Cookie userId = new Cookie("userId", String.valueOf(user.getId()));
            userId.setMaxAge(1800);
            Cookie userName = new Cookie("userName", user.getName());
            userName.setMaxAge(1800);
            resp.addCookie(userId);
            resp.addCookie(userName);

            HttpSession session = req.getSession(true);

            if(session != null) {
                if (usersOnline.containsValue(userName.getValue())) {
                    usersOnline.replace(session.getId(), userName.getValue());
                } else {
                    usersOnline.put(session.getId(), userName.getValue());
                }
            }
            logger.info("User '" + user.getName() + "' have been connected");
            resp.sendRedirect("chat.html");
        }
        else {
            resp.getWriter().write("incorrect password");
        }
    }

}
