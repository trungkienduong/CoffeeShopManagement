module com.example.coffeeshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;

    exports BUS;
    exports DAO;
    exports MODEL;
    exports GUI.CONTROLLER.PANEL;
    exports GUI.CONTROLLER.DIALOG;
    exports UTIL;

    opens GUI.CONTROLLER.PANEL to javafx.fxml;
    opens GUI.CONTROLLER.DIALOG to javafx.fxml;
    opens MODEL to javafx.base;
    opens MAIN;

}
