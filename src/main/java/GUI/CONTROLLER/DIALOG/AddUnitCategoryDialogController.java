package GUI.CONTROLLER.DIALOG;

import BUS.UnitCategoryBUS;
import MODEL.UnitCategory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUnitCategoryDialogController {

    @FXML
    private TextField unitNameField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private final UnitCategoryBUS unitCategoryBUS = UnitCategoryBUS.getInstance();

    @FXML
    private void initialize() {
        // Gán sự kiện cho nút "Xác nhận"
        confirmButton.setOnAction(event -> handleConfirm());

        // Gán sự kiện cho nút "Hủy"
        cancelButton.setOnAction(event -> handleCancel());
    }

    private void handleConfirm() {
        String unitName = unitNameField.getText().trim();

        if (unitName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập tên đơn vị.");
            return;
        }

        if (!unitCategoryBUS.isUnitCategoryUnique(unitName)) {
            showAlert(Alert.AlertType.ERROR, "Tên đơn vị đã tồn tại.");
            return;
        }

        UnitCategory newUnit = new UnitCategory();
        newUnit.setUnitName(unitName);

        boolean success = unitCategoryBUS.insertCategory(newUnit);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thêm đơn vị thành công!");
            closeDialog();
        } else {
            showAlert(Alert.AlertType.ERROR, "Không thể thêm đơn vị. Vui lòng thử lại.");
        }
    }

    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}