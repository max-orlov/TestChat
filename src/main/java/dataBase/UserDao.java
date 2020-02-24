package dataBase;

import model.User;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserDao {
    private static Connection conn = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/my_chat";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private static final Logger logger = Logger.getLogger(UserDao.class);

    static {
        {
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Connection successful");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
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

    public static User getByName(String userName) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from users where name = ?");
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        Long id = 0L;
        String name = null;
        String FIO = "";
        String tel = "";
        String e_mail = "";
        String password = "";
        while (rs.next()) {
            id = rs.getLong("id");
            name = rs.getString("name");
            FIO = rs.getString("fio");
            tel = rs.getString("tel");
            e_mail = rs.getString("e_mail");
            password = rs.getString("password");
        }
        if (name == null) {
            return null;
        }
        User user = new User(id, name, FIO, tel, e_mail, password);
        ps.close();
        logger.info("User " + "'" + user.getName() + "'" + " have been founded");
        return user;
    }

    public static User getById(Long userId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        Long id = null;
        String name = "";
        String FIO = "";
        String tel = "";
        String e_mail = "";
        String password = "";
        while (rs.next()) {
            id = rs.getLong("id");
            name = rs.getString("name");
            FIO = rs.getString("fio");
            tel = rs.getString("tel");
            e_mail = rs.getString("e_mail");
            password = rs.getString("password");
        }
        if (id == null) {
            return null;
        }
        User user = new User(id, name, FIO, tel, e_mail, password);
        ps.close();
        logger.info("User " + "'" + user.getName() + "'" + " have been founded");
        return user;
    }

    public static void addUser(User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into users(name, fio, tel, e_mail, password) values(?, ?, ?, ?, ?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getFIO());
        ps.setString(3, user.getTel());
        ps.setString(4, user.getE_mail());
        ps.setString(5, user.getPassword());
        ps.execute();
        ps.close();
        logger.info("User " + "'" + user.getName() + "'" + " have been added");
    }

}
