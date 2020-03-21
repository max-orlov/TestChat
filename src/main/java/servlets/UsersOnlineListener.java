package servlets;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

@WebListener
public class UsersOnlineListener implements HttpSessionListener {

    private static Logger logger = Logger.getLogger(UsersOnlineListener.class);
    private Map<String, String> usersOnline = UsersOnlineServlet.getUsersOnline();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(60);
        logger.info("Создана сессия --- " + session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        usersOnline.remove(session.getId());
        logger.info("Удалена сессия --- " + session.getId());
    }
}
