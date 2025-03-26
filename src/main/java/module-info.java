module com.example.coffeeshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.coffeeshopmanagement to javafx.fxml;

    exports com.example.coffeeshopmanagement;
    exports DAO;
}