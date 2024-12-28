package org.example.inventory.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    // Initialize database and create schema/tables
    public static void initializeDatabase() {
        // Connect to MySQL (without specifying a database) to check for the existence of 'inventory'
        try (Connection connection = MySQLConnection.getRootConnection()) {

            // Check if the 'inventory' database exists
            if (!doesDatabaseExist(connection)) {
                // If the database doesn't exist, create it
                createDatabase(connection);
                System.out.println("Database 'inventory' created successfully.");
            }

            // Now that we have ensured 'inventory' exists, connect to it and create schema and data
            try (Connection inventoryConnection = MySQLConnection.getConnection()) {
                // Create tables and insert data programmatically (instead of using SQL files)
                createTables(inventoryConnection);
                insertSampleData(inventoryConnection);

                System.out.println("Database initialization complete.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to initialize database.");
            e.printStackTrace();
        }
    }

    // Check if the 'inventory' database exists
    private static boolean doesDatabaseExist(Connection connection) throws SQLException {
        String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'inventory'";
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(query).next(); // Returns true if the database exists
        }
    }

    // Create 'inventory' database if it doesn't exist
    private static void createDatabase(Connection connection) throws SQLException {
        String createDbQuery = "CREATE DATABASE inventory";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createDbQuery); // Executes the creation of the 'inventory' database
        }
    }

    // Create the necessary tables
    private static void createTables(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS products ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255) NOT NULL, "
                + "category VARCHAR(100), "
                + "quantity INT NOT NULL, "
                + "price DECIMAL(10, 2) NOT NULL"
                + ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableQuery); // Creates the 'products' table if it doesn't exist
            System.out.println("Products table created successfully.");
        }
    }

    // Insert sample data into the products table
    private static void insertSampleData(Connection connection) throws SQLException {
        String insertDataQuery = "INSERT INTO products (name, category, quantity, price) VALUES "
                + "('Laptop', 'Electronics', 10, 799.99), "
                + "('Smartphone', 'Electronics', 25, 399.99), "
                + "('Headphones', 'Accessories', 50, 49.99)";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(insertDataQuery); // Inserts sample data into 'products' table
            System.out.println("Sample data inserted successfully.");
        }
    }
}
