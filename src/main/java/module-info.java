module org.example.inventory {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.inventory to javafx.fxml;
    exports org.example.inventory.client;
    opens org.example.inventory.client to javafx.fxml;
    exports org.example.inventory.server;
    opens org.example.inventory.server to javafx.fxml;

    opens org.example.inventory.server.models to javafx.base;

    
}
