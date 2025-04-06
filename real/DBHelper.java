package real;
import java.sql.*;

public class DBHelper {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/PubSys";
    private static final String DB_USER = "Toj";  
    private static final String DB_PASS = "Maxcatman123!";      

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}