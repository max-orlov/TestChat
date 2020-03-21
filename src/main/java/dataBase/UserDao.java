package dataBase;

import model.User;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserDao {

    private static final Logger logger = Logger.getLogger(UserDao.class);
    private static Connection conn = DAO.getConn();

    public static User getByName(String userName) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from users where name = ?");
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        long id = 0L;
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
        logger.info("User '" + user.getName() + "' have been founded");
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
        logger.info("User '" + user.getName() + "' have been founded");
        return user;
    }

    public static void addUser(User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into users(name, fio, tel, e_mail, password) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getName());
        ps.setString(2, user.getFIO());
        ps.setString(3, user.getTel());
        ps.setString(4, user.getE_mail());
        ps.setString(5, user.getPassword());
        ps.execute();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        long id = generatedKeys.getLong(1);
        user.setId(id);
        ps.close();
        logger.info("User '" + user.getName() + "' have been added");
    }

}
