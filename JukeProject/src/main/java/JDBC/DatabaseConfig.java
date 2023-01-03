package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public static Connection getConnection() throws SQLException
    {
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Akrm#123");
        return con;
    }

}
