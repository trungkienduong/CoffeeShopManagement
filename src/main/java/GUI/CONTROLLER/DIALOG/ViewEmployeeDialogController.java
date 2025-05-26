package GUI.CONTROLLER.DIALOG;

import BUS.EmployeeBUS;
import BUS.UserBUS;
import MODEL.Employee;
import MODEL.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

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
    @FXML
    private Button closeButton;

    private Stage dialogStage;
    private final UserBUS userBUS;
    private final EmployeeBUS employeeBUS;

    public ViewEmployeeDialogController() {
        userBUS = UserBUS.getInstance();
        employeeBUS = EmployeeBUS.getInstance();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        User currentUser = userBUS.getCurrentUser();
        if (currentUser == null) {
            showEmptyData();
            return;
        }

        // Lấy Employee từ username của currentUser
        Employee currentEmployee = employeeBUS.getEmployeeByUsername(currentUser.getUsername());

        if (currentEmployee == null) {
            showEmptyData();
            return;
        }

        loadEmployeeInfo(currentEmployee);
    }

    private void loadEmployeeInfo(Employee employee) {
        // Ảnh
        if (employee.getImagePath() != null && !employee.getImagePath().isEmpty()) {
            try {
                Image image = new Image("file:" + employee.getImagePath());
                employeeImageView.setImage(image);
            } catch (Exception e) {
                employeeImageView.setImage(null);
            }
        } else {
            employeeImageView.setImage(null);
        }

        emailLabel.setText(userBUS.getCurrentUser().getEmail());
        roleLabel.setText(employee.getPosition() != null ? employee.getPosition().getPositionName() : "");
        fullnameLabel.setText(employee.getFullName() != null ? employee.getFullName() : "");
        genderLabel.setText(employee.getGender() != null ? (employee.getGender().toString().equals("MALE") ? "Nam" : "Nữ") : "");
        cccdLabel.setText(employee.getCccd() != null ? employee.getCccd() : "");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (employee.getDateOfBirth() != null) {
            birthDateLabel.setText(employee.getDateOfBirth().format(dtf));
        } else {
            birthDateLabel.setText("");
        }

        phoneLabel.setText(employee.getPhone() != null ? employee.getPhone() : "");
        salaryLabel.setText(String.valueOf(employee.getSalary()));
        addressLabel.setText(employee.getAddress() != null ? employee.getAddress() : "");
    }

    private void showEmptyData() {
        emailLabel.setText("Không có dữ liệu");
        roleLabel.setText("-");
        fullnameLabel.setText("-");
        genderLabel.setText("-");
        cccdLabel.setText("-");
        birthDateLabel.setText("-");
        phoneLabel.setText("-");
        salaryLabel.setText("-");
        addressLabel.setText("-");
        employeeImageView.setImage(null);
    }

    @FXML
    private void handleClose() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}
