module org.example.uiuniform {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens org.example.uiuniform to javafx.fxml;
    exports org.example.uiuniform;
}