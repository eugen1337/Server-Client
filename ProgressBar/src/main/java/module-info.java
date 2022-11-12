module com.example.progressbar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.progressbar to javafx.fxml;
    exports com.example.progressbar;
    exports Server;
    opens Server to javafx.fxml;
}