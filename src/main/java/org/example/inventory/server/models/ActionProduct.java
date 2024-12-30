package org.example.inventory.server.models;

import org.example.inventory.server.enums.ActionsType;
import org.example.inventory.server.models.Product;

import java.io.Serializable;

public class ActionProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    private ActionsType action; // e.g., "add", "update", "delete"
    private Product product; // Your Product object

    public ActionProduct(ActionsType action, Product product) {
        this.action = action;
        this.product = product;
    }

    // Getters and setters
    public ActionsType getAction() {
        return action;
    }



    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ActionProduct{" +
                "action='" + action + '\'' +
                ", product=" + product +
                '}';
    }
}
