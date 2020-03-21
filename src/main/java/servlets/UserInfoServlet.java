package servlets;

import com.google.gson.Gson;
import dataBase.UserDao;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(UserInfoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF8");
        if (req.getParameter("id") != null) {
            String id = req.getParameter("id");
            try {
                User user = UserDao.getById(Long.valueOf(id));
                if(user == null){
                    resp.getWriter().write("null");
                    return;
                }
                resp.getWriter().write(user.toString());
            } catch (SQLException e) {
                logger.error(e.getMessage());
                resp.getWriter().write(new Gson().toJson(new Error("Server error.")));
            }
        }
        if (req.getParameter("name") != null) {
            String name = req.getParameter("name");
            try {
                User user = UserDao.getByName(name);
                if(user == null){
                    resp.getWriter().write("null");
                    return;
                }
                resp.getWriter().write(user.toString());
            } catch (SQLException e) {
                logger.error(e.getMessage());
                resp.getWriter().write(new Gson().toJson(new Error("Server error.")));
            }
        }
    }
}
