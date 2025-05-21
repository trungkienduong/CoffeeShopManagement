package GUI.CONTROLLER.DIALOG;

import BUS.CategoryBUS;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AddInventoryItemDialogController {

    @FXML
    private TextField unitNameField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private CategoryBUS categoryBUS;

    @FXML
    private void initialize() {
        categoryBUS = new CategoryBUS();

        confirmButton.setOnAction(event -> onConfirm());
        cancelButton.setOnAction(event -> onCancel());
    }

    private void onConfirm() {
        String categoryName = unitNameField.getText();

        if (categoryName == null || categoryName.trim().isEmpty()) {
            showAlert("Lỗi", "Tên mặt hàng không được để trống.");
            return;
        }

        boolean success = categoryBUS.addCategory(categoryName.trim());

        if (success) {
            showAlert("Thành công", "Thêm mặt hàng mới thành công!");
            closeDialog();
        } else {
            showAlert("Lỗi", "Tên mặt hàng đã tồn tại hoặc có lỗi xảy ra.");
        }
    }

    private void onCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
