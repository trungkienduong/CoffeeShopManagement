package GUI.CONTROLLER.PANEL;

import BUS.InventoryBUS;
import MODEL.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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

    @FXML
    private void initialize() {
        inventoryBUS = InventoryBUS.getInstance();

        // Set cell value factory cho từng cột dựa trên tên getter trong Inventory
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("costPrice"));

        // Load dữ liệu lên bảng
        loadInventoryData();
    }

    private void loadInventoryData() {
        List<Inventory> list = inventoryBUS.getAll();
        ObservableList<Inventory> observableList = FXCollections.observableArrayList(list);
        inventoryTable.setItems(observableList);
    }

    @FXML
    private void handleAddInventory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/InventoryUpdateDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add New Product");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            // Sau khi dialog đóng, refresh bảng
            loadInventoryData();

        } catch (IOException e) {
            e.printStackTrace();
            // Có thể hiện Alert lỗi
        }
    }


    @FXML
    private void handleDeleteInventory(ActionEvent event) {
        Inventory selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("No item selected for deletion");
            return;
        }

        boolean success = inventoryBUS.deleteItem(selected.getItemName());
        if (success) {
            System.out.println("Deleted item: " + selected.getItemName());
            loadInventoryData();
        } else {
            System.out.println("Failed to delete item: " + selected.getItemName());
        }
    }


}
