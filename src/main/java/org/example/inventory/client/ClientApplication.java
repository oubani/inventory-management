package org.example.inventory.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("/org/example/inventory/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 750);
        scene.getStylesheets().add(getClass().getResource("/org/example/inventory/styles.css").toExternalForm());
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}