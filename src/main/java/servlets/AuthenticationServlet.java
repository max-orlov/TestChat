package servlets;

import com.google.gson.Gson;
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

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

     private Set<User> usersOnline = UsersOnline.getUsersOnline();
     private static Logger logger = Logger.getLogger(AuthenticationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = null;
        try {
            user = UserDao.getByName(name);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        if(user == null) {
            resp.getWriter().write("user doesn't exists");
            logger.info("User not found");
            return;
        }
        if(user.getPassword().equals(password)) {
            Cookie userId = new Cookie("userId", String.valueOf(user.getId()));
            userId.setMaxAge(3600);
            Cookie userName = new Cookie("userName", user.getName());
            userName.setMaxAge(3600);
            resp.addCookie(userId);
            resp.addCookie(userName);
            usersOnline.add(user);
            logger.info("User " + "'" + user.getName() + "'" +  " have been connected");
            resp.sendRedirect("chat.html");
        }
        else {
            resp.getWriter().write("incorrect password");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF8");
        resp.getWriter().write(new Gson().toJson(usersOnline));
    }
}
