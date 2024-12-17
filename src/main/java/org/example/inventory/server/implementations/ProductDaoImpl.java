package org.example.inventory.server.implementations;

import org.example.inventory.server.daos.ProductDao;
import org.example.inventory.server.database.MySQLConnection;
import org.example.inventory.server.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product save(Product product) {
        String query = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, product.getName());
                ps.setInt(2, product.getQuantity());
                ps.setDouble(3, product.getPrice());
                ps.executeUpdate();

                // Get generated ID
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
                System.out.println("Product saved with ID: " + product.getId());
            }
            return product;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(Product product) {
        System.out.println("Trying to update product: " + product.getId());

        String query = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            System.out.println("Setting parameters for update...");
            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getId());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Product updated successfully: " + product.getId());
                return product;
            } else {
                System.out.println("Product update failed: " + product.getId());
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public Boolean remove(Product product) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, product.getId());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product deleted: " + product.getId());
                return true;
            } else {
                System.out.println("Product deletion failed: " + product.getId());
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Product> getAll() {

        String query = "SELECT * FROM products";
         List<Product> products =new ArrayList<Product>();

        try {
            Connection connection = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet =  preparedStatement.executeQuery();

            System.out.println(resultSet);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                products.add(new Product(id,name,quantity,price));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> search(String text) {
        return null;
    }
}