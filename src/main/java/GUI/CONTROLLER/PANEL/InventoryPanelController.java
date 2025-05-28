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
    private User currentUser;

    @FXML
    private void initialize() {
        inventoryBUS = InventoryBUS.getInstance();

        currentUser = UserBUS.getInstance().getCurrentUser();

        // Phân quyền nút
        handleRolePermission();

        // Set cell value factory cho từng cột dựa trên tên getter trong Inventory
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("costPrice"));

        // Load dữ liệu lên bảng
        loadInventoryData();
    }

    private void handleRolePermission() {
        if (currentUser == null) {
            // Nếu không có user thì disable hết
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
            return;
        }

        if (currentUser.hasRole("EMPLOYEE")) {
            // EMPLOYEE ẩn add, delete
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        } else if (currentUser.hasRole("STAFF")) {
            // STAFF được thao tác bình thường, không disable nút
            addBtn.setDisable(false);
            deleteBtn.setDisable(false);
        } else {
            // Các role khác nếu có, có thể disable luôn cho an toàn
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }
    }

    private void loadInventoryData() {
        List<Inventory> list = inventoryBUS.getAll();
        ObservableList<Inventory> observableList = FXCollections.observableArrayList(list);
        inventoryTable.setItems(observableList);
    }

    @FXML
    private void handleAddInventory(ActionEvent event) {
        if (currentUser != null && currentUser.hasRole("EMPLOYEE")) {
            // EMPLOYEE không được thêm
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Không đủ quyền");
            alert.setHeaderText(null);
            alert.setContentText("Bạn không có quyền thêm nguyên liệu.");
            alert.showAndWait();
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
    private void handleDeleteInventory(ActionEvent event) {
        if (currentUser != null && currentUser.hasRole("EMPLOYEE")) {
            // EMPLOYEE không được xóa
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Không đủ quyền");
            alert.setHeaderText(null);
            alert.setContentText("Bạn không có quyền xóa nguyên liệu.");
            alert.showAndWait();
            return;
        }

        Inventory selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn nguyên liệu để xóa.");
            alert.showAndWait();
            return;
        }

        boolean success = inventoryBUS.deleteItem(selected.getItemName());
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Xóa nguyên liệu thành công.");
            alert.showAndWait();

            loadInventoryData();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Xóa nguyên liệu thất bại. Có thể nguyên liệu đang được sử dụng.");
            alert.showAndWait();
        }
    }
}
