package GUI.CONTROLLER.PANEL;

import BUS.InventoryBUS;
import BUS.UserBUS;
import MODEL.Inventory;
import MODEL.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class InventoryPanelController {

    @FXML
    private TableView<Inventory> inventoryTable;

    @FXML
    private TableColumn<Inventory, String> nameColumn;

    @FXML
    private TableColumn<Inventory, BigDecimal> quantityColumn;

    @FXML
    private TableColumn<Inventory, String> unitColumn;

    @FXML
    private TableColumn<Inventory, BigDecimal> priceColumn;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    private InventoryBUS inventoryBUS;
    private User currentUser;

    private Parent root;

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Parent getRoot() {
        return root;
    }

    @FXML
    private void initialize() {
        inventoryBUS = InventoryBUS.getInstance();
        currentUser = UserBUS.getInstance().getCurrentUser();
        applyRolePermissions();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("costPrice"));

        loadInventoryData();
    }

    private void applyRolePermissions() {
        if (currentUser == null) {
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
            return;
        }
        if (currentUser.hasRole("EMPLOYEE")) {
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        } else if (currentUser.hasRole("STAFF")) {
            addBtn.setDisable(false);
            deleteBtn.setDisable(false);
        } else {
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }
    }

    private void loadInventoryData() {
        List<Inventory> list = inventoryBUS.getAll();
        ObservableList<Inventory> observableList = FXCollections.observableArrayList(list);
        inventoryTable.setItems(observableList);
    }

    public void handleSearch(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadInventoryData();
            return;
        }

        String lowerKeyword = keyword.trim().toLowerCase();
        List<Inventory> allItems = inventoryBUS.getAll();

        List<Inventory> filtered = allItems.stream()
                .filter(item -> item.getItemName() != null && item.getItemName().toLowerCase().contains(lowerKeyword))
                .toList();

        ObservableList<Inventory> filteredList = FXCollections.observableArrayList(filtered);
        inventoryTable.setItems(filteredList);
    }

    @FXML
    private void handleAddInventory(ActionEvent event) {
        if (currentUser != null && currentUser.hasRole("EMPLOYEE")) {
            showAlert(Alert.AlertType.WARNING, "Insufficient Permission", "You do not have permission to add inventory.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/InventoryUpdateDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add New Inventory Item");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            loadInventoryData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteInventory(ActionEvent event) throws Exception {
        if (currentUser != null && currentUser.hasRole("EMPLOYEE")) {
            showAlert(Alert.AlertType.WARNING, "Insufficient Permission", "You do not have permission to delete inventory.");
            return;
        }

        Inventory selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an inventory item to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete the selected inventory item?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        boolean success = inventoryBUS.deleteItem(selected.getItemName());
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item deleted successfully.");
            loadInventoryData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete inventory item. It might be in use.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
