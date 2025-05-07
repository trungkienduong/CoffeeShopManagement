package GUI.CONTROLLER.PANEL;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class CoffeeShopGUIController {

    @FXML
    private Button homeBtn;

    @FXML
    private Button productBtn;

    @FXML
    private Button employeeBtn;

    @FXML
    private Button inventoryBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField searchField;

    @FXML
    private Label employeeCount;

    @FXML
    private Label productCount;

    @FXML
    private Label inventoryCount;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private AreaChart<String, Number> salesChart;

    @FXML
    private PieChart productChart;

    @FXML
    private void initialize() {
        updateDashboard();
    }

    @FXML
    private void handleNavigation(javafx.event.ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();

            if (clickedButton == homeBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/CoffeeShopGUI.fxml"));
                Parent root = loader.load();
                mainContent.getScene().setRoot(root);
            }
            else if (clickedButton == productBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/ProductPanel.fxml"));
                Parent productView = loader.load();
                mainContent.getChildren().setAll(productView);
            }
            else if (clickedButton == employeeBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/EmployeePanel.fxml"));
                Parent productView = loader.load();
                mainContent.getChildren().setAll(productView);
            }
            else if (clickedButton == inventoryBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/InventoryPanel.fxml"));
                Parent productView = loader.load();
                mainContent.getChildren().setAll(productView);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        if (!searchQuery.isEmpty()) {
            // Implement search functionality
        }
    }

    @FXML
    private void handleLogout() {
        // Implement logout functionality
    }

    private void updateDashboard() {
        // Update statistics from database
        // Update charts
    }
}