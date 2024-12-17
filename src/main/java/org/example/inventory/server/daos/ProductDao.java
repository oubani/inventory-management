package org.example.inventory.server.daos;

import org.example.inventory.server.models.Product;

import java.util.List;

public interface ProductDao {
    Product save (Product product);

    Product update (Product product);

    Boolean remove (Product product);

    List<Product> getAll();

    List<Product> search (String text);

}
