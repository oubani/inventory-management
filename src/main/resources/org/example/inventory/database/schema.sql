-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS inventory;

-- Switch to the 'inventory' database
USE inventory;

-- Create the products table
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);
