package servlets;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(value = "/usersOnline")
public class UsersOnlineServlet extends HttpServlet {

    private static Map<String, String> usersOnline = new HashMap<>();

    public static Map<String, String> getUsersOnline() {
        return usersOnline;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF8");
        resp.getWriter().write(new Gson().toJson(usersOnline.values()));
    }

}
