package GUI.CONTROLLER.PANEL;

import MODEL.Inventory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import BUS.EmployeeBUS;
import BUS.ProductBUS;
import BUS.InventoryBUS;
import javafx.scene.chart.PieChart.Data;
import MODEL.Product;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                productPanelController = loader.getController();
                mainContent.getChildren().setAll(productView);
            }
            else if (clickedButton == employeeBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/EmployeePanel.fxml"));
                Parent employeeView = loader.load();
                employeePanelController = loader.getController();
                mainContent.getChildren().setAll(employeeView);
            }
            else if (clickedButton == inventoryBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/InventoryPanel.fxml"));
                Parent inventoryView = loader.load();
                inventoryPanelController = loader.getController();
                mainContent.getChildren().setAll(inventoryView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();

        if (!searchQuery.isEmpty()) {
            if (employeePanelController != null) {
                employeePanelController.handleSearch(searchQuery);
            }
            else if (productPanelController != null) {
                productPanelController.handleSearch(searchQuery);
            }
            else if (inventoryPanelController != null) {
                inventoryPanelController.handleSearch(searchQuery);
            }
        } else {
            if (employeePanelController != null) {
                employeePanelController.handleSearch("");
            }
            else if (productPanelController != null) {
                productPanelController.handleSearch("");
            }
            else if (inventoryPanelController != null) {
                inventoryPanelController.handleSearch("");
            }
        }
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        DialogPane dialogPane = alert.getDialogPane();
        String css = getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css").toExternalForm();
        dialogPane.getStylesheets().add(css);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/LoginDialog.fxml"));
                Parent loginRoot = loader.load();

                Stage currentStage = (Stage) logoutBtn.getScene().getWindow();

                currentStage.setScene(new Scene(loginRoot));
                currentStage.setTitle("Login");
                currentStage.setResizable(false);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateDashboard() {
        int employeeTotal = EmployeeBUS.getInstance().getAllEmployees().size();
        employeeCount.setText(String.valueOf(employeeTotal));

        int productTotal = ProductBUS.getInstance().getAllProducts().size();
        productCount.setText(String.valueOf(productTotal));

        int inventoryTotal = InventoryBUS.getInstance().getAll().size();
        inventoryCount.setText(String.valueOf(inventoryTotal));

        updateProductChart();
        updateInventoryChart();
    }

    private void updateProductChart() {
        List<Product> productList = ProductBUS.getInstance().getAllProducts();

        Map<String, Integer> categoryCountMap = new HashMap<>();
        for (Product p : productList) {
            String categoryName = ProductBUS.getInstance().getCategoryNameById(p.getCategoryId());
            categoryCountMap.put(categoryName, categoryCountMap.getOrDefault(categoryName, 0) + 1);
        }

        productChart.getData().clear();
        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            productChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void updateInventoryChart() {
        List<Inventory> inventoryList = InventoryBUS.getInstance().getAll();

        XYChart.Series<String, Number> quantitySeries = new XYChart.Series<>();
        quantitySeries.setName("Inventory Quantity");

        for (Inventory item : inventoryList) {
            quantitySeries.getData().add(new XYChart.Data<>(item.getItemName(), item.getQuantity()));
        }

        salesChart.getData().clear();
        salesChart.getData().add(quantitySeries);
    }

    private EmployeePanelController employeePanelController;
    private InventoryPanelController inventoryPanelController;
    private ProductPanelController productPanelController;
}
