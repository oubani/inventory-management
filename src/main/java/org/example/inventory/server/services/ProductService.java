package org.example.inventory.server.services;

import org.example.inventory.server.daos.ProductDao;
import org.example.inventory.server.database.MySQLConnection;
import org.example.inventory.server.models.Product;

import java.sql.Connection;
import java.util.List;

public class ProductService   {
    Connection c =  MySQLConnection.getConnection();
}
