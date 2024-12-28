package org.example.inventory.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";  // Root connection URL (no database specified yet)
    private static final String USERNAME = "root";  // Your MySQL username
    private static final String PASSWORD = "";  // Your MySQL password
    private static final String DB_NAME = "inventory";  // Database name to be checked/created

    private MySQLConnection() {}

    // Get connection to MySQL server (no database specified)
    public static Connection getRootConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);  // Connect without specifying database
    }

    // Get connection to the 'inventory' database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USERNAME, PASSWORD);  // Connect to 'inventory' database
    }
}
