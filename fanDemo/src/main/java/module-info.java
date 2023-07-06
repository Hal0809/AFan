module com.example.fandemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fandemo to javafx.fxml;
    exports com.example.fandemo;
}