package GUI.CONTROLLER.DIALOG;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import MODEL.Employee;

public class ViewEmployeeDialogController {

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

    @FXML
    private ImageView employeeImageView;

    @FXML
    private Button closeButton;

    // Gọi khi truyền dữ liệu từ ngoài vào
    public void setEmployee(Employee employee) {
        if (employee == null) return;

        fullnameLabel.setText(employee.getFullName());
        genderLabel.setText(String.valueOf(employee.getGender()));
        cccdLabel.setText(employee.getCccd());
        birthDateLabel.setText(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : "");
        phoneLabel.setText(employee.getPhone());
        salaryLabel.setText(String.format("%.0f", employee.getSalary()));
        addressLabel.setText(employee.getAddress());
        emailLabel.setText(employee.getUser().getEmail());
        roleLabel.setText(employee.getPosition().getPositionName());

        // Load ảnh (nếu có URL hợp lệ, bạn có thể chỉnh lại đường dẫn cho phù hợp)
        if (employee.getImagePath() != null && !employee.getImagePath().isEmpty()) {
            try {
                Image image = new Image(employee.getImagePath());
                employeeImageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Không thể load ảnh nhân viên: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
