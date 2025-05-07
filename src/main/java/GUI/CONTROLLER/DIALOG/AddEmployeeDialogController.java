package GUI.CONTROLLER.DIALOG;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AddEmployeeDialogController {

    @FXML
    private ImageView employeeImageView;
    @FXML
    private Button imageButton;
    @FXML
    private Label emailLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private TextField fullnameField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField cccdField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField phoneField;
    @FXML
    private TextArea addressArea;
    @FXML
    private TextField salaryField;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() {
        // Gán tạm dữ liệu label (test)
        emailLabel.setText("example@gmail.com");
        roleLabel.setText("Nhân viên");

        // Xử lý nút ảnh
        imageButton.setOnAction(e -> {
            System.out.println("Đã nhấn nút chọn ảnh.");
            // Mở file hoặc hiển thị dialog chọn ảnh ở đây nếu muốn
        });

        // Xử lý xác nhận
        confirmButton.setOnAction(e -> {
            System.out.println("=== DỮ LIỆU NHẬP ===");
            System.out.println("Họ tên: " + fullnameField.getText());
            System.out.println("Giới tính: " + genderComboBox.getValue());
            System.out.println("CCCD: " + cccdField.getText());
            System.out.println("Ngày sinh: " + birthDatePicker.getValue());
            System.out.println("SĐT: " + phoneField.getText());
            System.out.println("Địa chỉ: " + addressArea.getText());
            System.out.println("Lương: " + salaryField.getText());
        });

        // Xử lý hủy
        cancelButton.setOnAction(e -> {
            // Đóng dialog nếu đang chạy trong Stage riêng
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
    }
}
