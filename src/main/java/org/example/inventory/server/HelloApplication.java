package org.example.inventory.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.inventory.database.DatabaseInitializer;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseInitializer.initializeDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/inventory/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 750);
        scene.getStylesheets().add(getClass().getResource("/org/example/inventory/styles.css").toExternalForm());
        stage.setTitle("Hello again!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}