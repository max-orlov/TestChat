package dataBase;

import model.Message;
import model.User;
import org.apache.log4j.Logger;
import org.postgresql.util.PGTimestamp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private static Connection conn = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/my_chat";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private static Logger logger = Logger.getLogger(MessageDao.class);

    static {
        {
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Connection successful");
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Message> getAllMessages() throws SQLException {
        List<Message> messages = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("select * from messages");
        ResultSet rs = ps.executeQuery();
        Long id;
        Date date;
        String text;
        User user;
        while (rs.next()) {
            id = rs.getLong("id");
            date = rs.getDate("date");
            text = rs.getString("text");
            user = UserDao.getById(rs.getLong("user_id"));
            Message message = new Message(id, date, text, user);
            messages.add(message);
        }
        ps.close();
        return messages;
    }

    public static void addMessage(Message message) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into messages(date, text, user_id) values (?, ?, ?)");
        ps.setTimestamp(1, new PGTimestamp(message.getDate().getTime()));
        ps.setString(2, message.getText());
        ps.setLong(3, message.getUser().getId());
        ps.execute();
        ps.close();
        logger.info("User " + "'" + message.getUser().getName() + "'" + " sent message " + "\"" +
                message.getText() + "\"");
    }
}
