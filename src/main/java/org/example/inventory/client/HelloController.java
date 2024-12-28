package org.example.inventory.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.example.inventory.server.implementations.ProductDaoImpl;
import org.example.inventory.server.models.Product;

import java.util.List;

public class HelloController {

    private ProductDaoImpl productDao = new ProductDaoImpl();

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> columnName;
    @FXML
    private TableColumn<Product, String> columnCategory;
    @FXML
    private TableColumn<Product, Integer> columnId;
    @FXML
    private TableColumn<Product, Double> columnPrice;
    @FXML
    private TableColumn<Product, Integer> columnQuantity;
    @FXML
    private TableColumn<Product, Void> columnAction;

    private ObservableList<Product> productsList = FXCollections.observableArrayList();

    @FXML
    private Label warningText;

    // Product input fields
    @FXML
    private TextField productName;
    @FXML
    private TextField productCategory;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productQuantity;
    @FXML
    private TextField productId;

    @FXML
    private Button clearFormButton;

    // Search form
    @FXML
    private TextField search;

    @FXML
    private Button productAddButton;
    private Boolean isEdit = false;  // Indicates if we are editing an existing product

    @FXML
    public void initialize() {
        System.out.println("this fired");

        // Fetch products from the database
        List<Product> products = productDao.getAll();
        System.out.println(products);

        productsList.setAll(products);

        // Set up TableView columns
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        columnAction.setCellFactory(param -> new TableCell<Product, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

// In your initialization method or where you set up the buttons

            // If you're using an HBox to contain both buttons

            {
                HBox actionButtonContainer = new HBox(editButton, deleteButton);
                actionButtonContainer.getStyleClass().add("action-button-container");
                editButton.setOnAction(event -> handleEdit(getTableRow().getItem()));
                deleteButton.setOnAction(event -> handleDelete(getTableRow().getItem()));

                editButton.getStyleClass().addAll("action-button", "edit-button");
                deleteButton.getStyleClass().addAll("action-button", "delete-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Remove buttons if the row is empty
                } else {
                    HBox hbox = new HBox(10, editButton, deleteButton);
                    HBox.setHgrow(editButton, Priority.ALWAYS);
                    setGraphic(hbox);
                }
            }
        });

        tableView.setItems(productsList);
    }

    private void handleEdit(Product product) {
        System.out.println("Editing product: " + product.getId());

        // Set the form fields with the product data
        productName.setText(product.getName());
        productCategory.setText(product.getCategory());
        productPrice.setText(String.valueOf(product.getPrice()));
        productQuantity.setText(String.valueOf(product.getQuantity()));
        productId.setText(String.valueOf(product.getId()));  // Maybe disable this field for editing

        isEdit = true;  // Mark that we are in edit mode
        productAddButton.setText("Update Product");
        clearFormButton.setVisible(true);
    }

    private void handleDelete(Product product) {
        System.out.println("Deleting product with id: " + product.getId());

        // Delete the product from the database
        productDao.remove(product);

        // Remove the product from the ObservableList
        productsList.remove(product);

        warningText.setText("Product deleted successfully");
    }


    @FXML
    protected void onCreateProductButton(ActionEvent event) {
        String productNameText = productName.getText();
        String productCategoryText = productCategory.getText();
        String productPriceText = productPrice.getText();
        String productQuantityText = productQuantity.getText();
        String productIdText = productId.getText();

        System.out.println("Product id is: " + productIdText);

        if (productNameText.isEmpty()) {
            warningText.setText("Product name cannot be empty");
            return;
        }
        if (productCategoryText.isEmpty()) {
            warningText.setText("Product name cannot be empty");
            return;
        }

        Double price = null;
        try {
            price = Double.parseDouble(productPriceText);
        } catch (NumberFormatException e) {
            warningText.setText("Invalid price, please insert a valid price");
            return;
        }

        Integer quantity = null;
        try {
            quantity = Integer.parseInt(productQuantityText);
        } catch (NumberFormatException e) {
            warningText.setText("Invalid quantity, Please Insert a valid quantity");
            return;
        }

        Product product = new Product(productNameText,productCategoryText ,quantity, price);

        if (isEdit) {
            product.setId(Integer.parseInt(productIdText)); // Ensure product ID is set for editing
            System.out.println("Trying to update product with ID: " + product.getId());

            // Update the product in the database
            productDao.update(product);

            // Find the index of the product in the list and update it
            int index = productsList.indexOf(product);
            if (index >= 0) {
                productsList.set(index, product); // Update the product in the list
            }

            warningText.setText("Product updated successfully");
            isEdit = false;  // Reset edit mode
        } else {
            // Save a new product
            productDao.save(product);
            productsList.add(product); // Add new product to the list
            warningText.setText("Product added successfully");
        }

        clearForm();
    }


    @FXML
    protected void onSearchProducts(ActionEvent event) {
        String searchText = search.getText();

        System.out.println("Searching for: " + searchText);

        List<Product> products = productDao.search(searchText);

        productsList.setAll(products);

    }

    @FXML
    protected void onClearForm(ActionEvent event) {
        System.out.println("Clearing form");
        clearForm();
    }

    private void clearForm() {
        productName.clear();
        productCategory.clear();
        productPrice.clear();
        productQuantity.clear();
        productId.clear();
        isEdit = false;  // Reset edit mode when clearing the form
        productAddButton.setText("Add Product");
        clearFormButton.setVisible(false);
    }
}
