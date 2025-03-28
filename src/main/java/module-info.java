module com.example.coffeeshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Nếu dùng database
    requires javafx.media; // 🔥 Thêm dòng này để dùng MediaView

    // Mở package chứa FXML
    opens FXML.DIALOG to javafx.fxml;
    opens FXML.PANEL to javafx.fxml;

    // Mở package chứa Controller để JavaFX có thể khởi tạo
    opens GUI.CONTROLLER.DIALOG to javafx.fxml;
    opens GUI.CONTROLLER.PANEL to javafx.fxml;

    // Mở package MAIN nếu có class khởi chạy
    opens MAIN to javafx.fxml;

    // Xuất các package cần thiết
    exports MAIN;
    exports GUI.CONTROLLER.DIALOG;
    exports GUI.CONTROLLER.PANEL;
}
