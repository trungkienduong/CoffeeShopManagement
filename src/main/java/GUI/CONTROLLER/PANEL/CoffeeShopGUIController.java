package GUI.CONTROLLER.PANEL;

import MODEL.Inventory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import BUS.EmployeeBUS;
import BUS.ProductBUS;
import BUS.InventoryBUS;
import javafx.scene.chart.PieChart.Data;
import MODEL.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Số lượng nhân viên
        int employeeTotal = EmployeeBUS.getInstance().getAllEmployees().size();
        employeeCount.setText(String.valueOf(employeeTotal));

        // Số lượng sản phẩm
        int productTotal = ProductBUS.getInstance().getAllProducts().size();
        productCount.setText(String.valueOf(productTotal));

        // Số lượng nguyên liệu tồn kho
        int inventoryTotal = InventoryBUS.getInstance().getAll().size();
        inventoryCount.setText(String.valueOf(inventoryTotal));

        updateProductChart();     // Pie chart sản phẩm theo loại
        updateInventoryChart();   // Area chart tồn kho nguyên liệu
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
        quantitySeries.setName("Tồn kho");

        for (Inventory item : inventoryList) {
            quantitySeries.getData().add(new XYChart.Data<>(item.getItemName(), item.getQuantity()));
        }

        salesChart.getData().clear();
        salesChart.getData().add(quantitySeries);
    }
}