package GUI.CONTROLLER.PANEL;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class CoffeeShopGUIController implements Initializable {

    @FXML private TextField searchField;
    @FXML private Button homeBtn;
    @FXML private Button productsBtn;
    @FXML private Button employeesBtn;
    @FXML private Button inventoryBtn;

    @FXML private StackPane contentArea;
    @FXML private VBox dashboardView;
    @FXML private VBox productsView;
    @FXML private VBox employeesView;
    @FXML private VBox inventoryView;

    @FXML private Label totalProductsLabel;
    @FXML private Label totalEmployeesLabel;
    @FXML private Label totalInventoryLabel;

    @FXML private BarChart<String, Number> salesChart;
    @FXML private PieChart categoryChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupSearch();
        loadDashboardData();
        initializeCharts();
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Implement search functionality
            performSearch(newValue);
        });
    }

    private void performSearch(String query) {
        // Implement search logic
        System.out.println("Searching for: " + query);
    }

    private void loadDashboardData() {
        // Load actual data from your database/service
        totalProductsLabel.setText("150");
        totalEmployeesLabel.setText("25");
        totalInventoryLabel.setText("300");
    }

    private void initializeCharts() {
        // Initialize Sales Chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales 2024");
        series.getData().add(new XYChart.Data<>("Jan", 2500));
        series.getData().add(new XYChart.Data<>("Feb", 3000));
        series.getData().add(new XYChart.Data<>("Mar", 2800));
        salesChart.getData().add(series);

        // Initialize Category Chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Coffee", 40),
                new PieChart.Data("Tea", 30),
                new PieChart.Data("Pastries", 20),
                new PieChart.Data("Others", 10)
        );
        categoryChart.setData(pieChartData);
    }

    @FXML
    private void handleHome() {
        dashboardView.setVisible(true);
        productsView.setVisible(false);
        employeesView.setVisible(false);
        inventoryView.setVisible(false);
    }

    @FXML
    private void handleProducts() {
        dashboardView.setVisible(false);
        productsView.setVisible(true);
        employeesView.setVisible(false);
        inventoryView.setVisible(false);
    }

    @FXML
    private void handleEmployees() {
        dashboardView.setVisible(false);
        productsView.setVisible(false);
        employeesView.setVisible(true);
        inventoryView.setVisible(false);
    }

    @FXML
    private void handleInventory() {
        dashboardView.setVisible(false);
        productsView.setVisible(false);
        employeesView.setVisible(false);
        inventoryView.setVisible(true);
    }
}
