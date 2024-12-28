package org.example.inventory.server.services;


import java.sql.Connection;
import java.sql.SQLException;

import org.example.inventory.database.MySQLConnection;

public class ProductService   {
    Connection c;

    {
        try {
            c = MySQLConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
