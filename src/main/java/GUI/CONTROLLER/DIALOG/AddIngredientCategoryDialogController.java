package GUI.CONTROLLER.DIALOG;

import BUS.IngredientCategoryBUS;
import MODEL.IngredientCategory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AddIngredientCategoryDialogController {

    @FXML
    private TextField unitNameField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private IngredientCategoryBUS categoryBUS;

    @FXML
    private void initialize() {
        categoryBUS = IngredientCategoryBUS.getInstance();

        confirmButton.setOnAction(event -> onConfirm());
        cancelButton.setOnAction(event -> onCancel());
    }

    private void onConfirm() {
        String categoryName = unitNameField.getText();

        if (categoryName == null || categoryName.trim().isEmpty()) {
            showAlert("Lỗi", "Tên loại nguyên liệu không được để trống.");
            return;
        }

        IngredientCategory category = new IngredientCategory();
        category.setIngredientCategoryName(categoryName.trim());

        boolean success = categoryBUS.addCategory(category);

        if (success) {
            showAlert("Thành công", "Thêm loại nguyên liệu mới thành công!");
            closeDialog();
        } else {
            showAlert("Lỗi", "Tên loại nguyên liệu đã tồn tại hoặc có lỗi xảy ra.");
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
