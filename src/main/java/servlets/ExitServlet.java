package servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/exit")
public class ExitServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(ExitServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        HttpSession session = req.getSession();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userName")) {
                logger.info("User " + "'" + cookie.getValue() + "'" + " leave chat");
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

        session.invalidate();
        resp.sendRedirect("index.html");
    }
}
