module com.example.coffeeshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.coffeeshopmanagement to javafx.fxml;

    exports com.example.coffeeshopmanagement;
}