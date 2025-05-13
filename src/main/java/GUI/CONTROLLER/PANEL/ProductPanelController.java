package GUI.CONTROLLER.PANEL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductPanelController {

    @FXML
    private FlowPane productContainer;

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
        // Nếu muốn load dữ liệu sản phẩm sau này, xử lý ở đây
    }

    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddProductDialog.fxml")); // sửa lại đường dẫn nếu cần
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
}
