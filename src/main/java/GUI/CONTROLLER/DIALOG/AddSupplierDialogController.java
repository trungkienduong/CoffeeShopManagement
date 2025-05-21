package GUI.CONTROLLER.DIALOG;

import BUS.SupplierBUS;
import MODEL.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddSupplierDialogController {

    @FXML
    private TextField supplierNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea addressArea;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private final SupplierBUS supplierBUS = SupplierBUS.getInstance();

    @FXML
    public void initialize() {
        // Xử lý sự kiện cho nút xác nhận
        confirmButton.setOnAction(event -> {
            if (validateInput()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierName(supplierNameField.getText().trim());
                supplier.setPhone(phoneField.getText().trim());
                supplier.setEmail(emailField.getText().trim());
                supplier.setAddress(addressArea.getText().trim());

                boolean success = supplierBUS.addSupplier(supplier);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm nhà cung cấp mới.");
                    closeDialog();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Thêm nhà cung cấp thất bại. Vui lòng kiểm tra lại.");
                }
            }
        });

        // Xử lý sự kiện cho nút hủy
        cancelButton.setOnAction(event -> closeDialog());
    }

    // Kiểm tra dữ liệu đầu vào cơ bản
    private boolean validateInput() {
        String name = supplierNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Thiếu dữ liệu", "Vui lòng nhập tên nhà cung cấp.");
            return false;
        }

        if (!phone.matches("\\d{9,15}")) {  // Ví dụ kiểm tra số điện thoại: 9-15 số
            showAlert(Alert.AlertType.WARNING, "Dữ liệu không hợp lệ", "Số điện thoại không hợp lệ (chỉ chứa 9-15 chữ số).");
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            showAlert(Alert.AlertType.WARNING, "Dữ liệu không hợp lệ", "Email không hợp lệ.");
            return false;
        }

        // Có thể thêm kiểm tra cho địa chỉ nếu cần

        return true;
    }

    // Hiện hộp thoại thông báo
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Đóng dialog hiện tại
    private void closeDialog() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
