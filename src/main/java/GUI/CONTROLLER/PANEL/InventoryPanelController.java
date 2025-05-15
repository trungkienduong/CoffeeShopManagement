package GUI.CONTROLLER.PANEL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryPanelController {

    @FXML
    private TableView inventoryTable;

    @FXML
    private Button addBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button viewBtn;

    @FXML
    private void initialize() {
        // Nếu muốn load dữ liệu sau này, xử lý ở đây
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

            // Sau khi dialog đóng, có thể refresh danh sách sản phẩm nếu cần
            // loadProducts();

        } catch (IOException e) {
            e.printStackTrace();
            // Có thể show Alert thay vì chỉ in lỗi
        }
    }

    @FXML
    private void handleEditInventory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/InventoryUpdateDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Product");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            // Sau khi dialog đóng, có thể refresh danh sách sản phẩm nиф cần
            // loadProducts();

        } catch (IOException e) {
            e.printStackTrace();
            // Có thể show Alert thay vì chỉ in lỗi nиф muốn
        }
    }

    @FXML
    private void handleDeleteInventory(ActionEvent event) {
        // Xử lý xóa sản phẩm tại đây
        System.out.println("Delete product");
    }

    @FXML
    private void handleViewinventory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/InventoryUpdateDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("View Product");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();  // Chờ dialog đóng newcom

            // Sau khi dialog đóng, có thể refresh danh sách sản phẩm nиф cần            // loadProducts();

        } catch (IOException e) {
            e.printStackTrace();
            // Có thể show Alert thay vì chỉ in lỗi nиф muốn
        }
    }
}
