package GUI.CONTROLLER.DIALOG;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class ViewEmployeeDialogController {

    @FXML
    private ImageView employeeImageView;

    @FXML
    private Label emailLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label fullnameLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label cccdLabel;

    @FXML
    private Label birthDateLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label salaryLabel;

    @FXML
    private Label addressLabel;

    // Khởi tạo controller
    @FXML
    private void initialize() {
        // Gán ảnh từ internet (ảnh ổn định và nhẹ)
        try {
            Image image = new Image("https://randomuser.me/api/portraits/men/75.jpg", true);
            employeeImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh: " + e.getMessage());
        }

        // Gán dữ liệu giả lập
        emailLabel.setText("nguyenvana@gmail.com");
        roleLabel.setText("Nhân viên pha chế");
        fullnameLabel.setText("Nguyễn Văn A");
        genderLabel.setText("Nam");
        cccdLabel.setText("123456789012");
        birthDateLabel.setText("01/01/2000");
        phoneLabel.setText("0901234567");
        salaryLabel.setText("7,000,000 VND");
        addressLabel.setText("123 Đường Trà Sữa, Quận Cà Phê, TP. Đà Lạt");
    }

    // Xử lý khi nhấn nút Đóng
    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
