module com.example.sc3javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens client to javafx.fxml;
    exports client;
}