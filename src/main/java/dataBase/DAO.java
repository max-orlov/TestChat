package dataBase;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

class DAO {
    private static Connection conn;

    static {
        Context ctx;
        try {
            ctx = new InitialContext();
            Context initContext = (Context) ctx.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("jdbc/postgres");
            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    static Connection getConn() {
        return conn;
    }

}
