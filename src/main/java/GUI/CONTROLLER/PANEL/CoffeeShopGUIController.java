package GUI.CONTROLLER.PANEL;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class CoffeeShopGUIController implements Initializable {
    @FXML private Button homeBtn;
    @FXML private Button productBtn;
    @FXML private Button employeeBtn;
    @FXML private Button inventoryBtn;
    @FXML private Button logoutBtn;

    @FXML private TextField searchField;

    @FXML private Label employeeCount;
    @FXML private Label productCount;
    @FXML private Label inventoryCount;

    @FXML private AreaChart<String, Number> salesChart;
    @FXML private PieChart productChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadStatistics();
        setupCharts();
        setupButtonStyles();
    }

    private void loadStatistics() {
        // TODO: Load actual data from your database
        employeeCount.setText("25");
        productCount.setText("150");
        inventoryCount.setText("300");
    }

    private void setupCharts() {
        // Setup Sales Chart
        salesChart.getData().clear();
        // TODO: Add your sales data series here

        // Setup Product Distribution Chart
        productChart.getData().clear();
        // TODO: Add your product distribution data here
    }

    private void setupButtonStyles() {
        // Add hover effect and click listeners to navigation buttons
        Button[] navButtons = {homeBtn, productBtn, employeeBtn, inventoryBtn};
        for (Button btn : navButtons) {
            btn.getStyleClass().add("nav-button");
        }
    }

    @FXML
    private void handleNavigation(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Remove selected style from all buttons
        homeBtn.getStyleClass().remove("selected");
        productBtn.getStyleClass().remove("selected");
        employeeBtn.getStyleClass().remove("selected");
        inventoryBtn.getStyleClass().remove("selected");

        // Add selected style to clicked button
        clickedButton.getStyleClass().add("selected");

        // TODO: Handle navigation logic based on clicked button
        if (clickedButton == homeBtn) {
            // Navigate to home
        } else if (clickedButton == productBtn) {
            // Navigate to products
        } else if (clickedButton == employeeBtn) {
            // Navigate to employees
        } else if (clickedButton == inventoryBtn) {
            // Navigate to inventory
        }
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        String searchText = searchField.getText();
        // TODO: Implement search functionality
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // TODO: Implement logout functionality
    }
}