package org.example.inventory.server.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private MySQLConnection() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to connect to MySQL server.");
            e.printStackTrace();
            throw new RuntimeException("Database connection error");
        }
    }

}
