module com.example.coffeeshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Nếu dùng database
    requires javafx.media; // 🔥 Thêm dòng này để dùng MediaView
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;

    exports BUS;
    exports DAO;
    exports MODEL;
    exports GUI.CONTROLLER.PANEL;
    exports GUI.CONTROLLER.DIALOG; // Cho phép Controller gọi từ GUI hoặc từ main

    opens GUI.CONTROLLER.PANEL to javafx.fxml; // Để FXMLLoader có thể truy cập các Controller
    opens GUI.CONTROLLER.DIALOG to javafx.fxml;
    opens MODEL to javafx.base; // Nếu có sử dụng property bindings trong model
    opens MAIN;



    /*// Mở package chứa FXML
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
    exports GUI.CONTROLLER.PANEL;*/
}
