module org.example.uiuniform {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.uiuniform to javafx.fxml;
    exports org.example.uiuniform;
}