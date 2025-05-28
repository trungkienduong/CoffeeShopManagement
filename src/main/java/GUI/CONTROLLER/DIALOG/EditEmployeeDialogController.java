package GUI.CONTROLLER.DIALOG;

import BUS.EmployeeBUS;
import BUS.EmployeePositionBUS;
import BUS.UserBUS;
import MODEL.Employee;
import MODEL.EmployeePosition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class EditEmployeeDialogController {

    @FXML private TextField emailField;
    @FXML private TextField fullNameField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField cccdField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private ComboBox<EmployeePosition> positionComboBox;
    @FXML private TextField salaryField;
    @FXML private ImageView employeeImageView;
    @FXML private Button browseButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final EmployeeBUS employeeBUS = EmployeeBUS.getInstance();
    private final EmployeePositionBUS positionBUS = EmployeePositionBUS.getInstance();
    private final UserBUS userBUS = UserBUS.getInstance();

    private Stage dialogStage;
    private String imagePath;
    private Employee currentEmployee;

    public void setEmployee(Employee employee) {
        this.currentEmployee = employee;
        if (employee == null) return;

        fullNameField.setText(employee.getFullName());
        genderComboBox.setValue(employee.getGender() == Employee.Gender.MALE ? "Nam" : "Nữ");
        cccdField.setText(employee.getCccd());
        birthDatePicker.setValue(employee.getDateOfBirth());
        phoneField.setText(employee.getPhone());
        addressField.setText(employee.getAddress());
        positionComboBox.setValue(employee.getPosition());
        salaryField.setText(String.valueOf(employee.getSalary()));
        imagePath = employee.getImagePath();

        if (imagePath != null) {
            File file = new File(imagePath);
            if (file.exists()) {
                employeeImageView.setImage(new Image(file.toURI().toString()));
            }
        }

        // Lấy email ưu tiên từ employee.getUser(), nếu null thì dùng UserBUS lấy theo username
        String email = "";
        if (employee.getUser() != null && employee.getUser().getEmail() != null && !employee.getUser().getEmail().isEmpty()) {
            email = employee.getUser().getEmail();
        } else if (employee.getUsername() != null && !employee.getUsername().isEmpty()) {
            email = userBUS.getEmailByUsername(employee.getUsername());
        }
        emailField.setText(email);
    }



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        genderComboBox.getItems().addAll("Nam", "Nữ");

        List<EmployeePosition> positions = positionBUS.getAllPositions();
        positionComboBox.getItems().addAll(positions);
        positionComboBox.setConverter(new StringConverter<EmployeePosition>() {
            @Override
            public String toString(EmployeePosition position) {
                return position != null ? position.getPositionName() : "";
            }
            @Override
            public EmployeePosition fromString(String string) {
                return null; // Không cần xử lý
            }
        });

        // Nếu muốn load employee tự động khi mở dialog (nếu chưa set employee từ bên ngoài)
        // bạn có thể bỏ comment đoạn dưới
        /*
        if (currentEmployee == null) {
            loadCurrentEmployee();
        }
        */
    }

    private void loadCurrentEmployee() {
        if (userBUS.getCurrentUser() == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không xác định người dùng hiện tại.");
            return;
        }

        currentEmployee = employeeBUS.getEmployeeByUsername(userBUS.getCurrentUser().getUsername());

        if (currentEmployee == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không tìm thấy thông tin nhân viên.");
            return;
        }

        setEmployee(currentEmployee);  // Gọi lại để đồng bộ dữ liệu lên form
    }

    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(dialogStage);
        if (file != null) {
            imagePath = file.getAbsolutePath();
            employeeImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void handleSave() {
        if (!validateInput()) return;

        try {
            currentEmployee.setFullName(fullNameField.getText().trim());
            currentEmployee.setGender(genderComboBox.getValue().equals("Nam") ? Employee.Gender.MALE : Employee.Gender.FEMALE);
            currentEmployee.setCccd(cccdField.getText().trim());
            currentEmployee.setDateOfBirth(birthDatePicker.getValue());
            currentEmployee.setPhone(phoneField.getText().trim());
            currentEmployee.setAddress(addressField.getText().trim());
            currentEmployee.setPosition(positionComboBox.getValue());
            currentEmployee.setSalary(Double.parseDouble(salaryField.getText().trim()));
            currentEmployee.setImagePath(imagePath);

            if (employeeBUS.updateEmployee(currentEmployee)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật thông tin thành công!");
                if (dialogStage != null) dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật nhân viên.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        if (fullNameField.getText() == null || fullNameField.getText().trim().isEmpty()) {
            errorMessage.append("Họ tên không được trống!\n");
        }
        if (genderComboBox.getValue() == null) {
            errorMessage.append("Vui lòng chọn giới tính!\n");
        }
        if (cccdField.getText() == null || cccdField.getText().trim().isEmpty()) {
            errorMessage.append("CCCD không được trống!\n");
        }

        LocalDate birthDate = birthDatePicker.getValue();
        LocalDate minDate = LocalDate.now().minusYears(18);
        if (birthDate == null || birthDate.isAfter(minDate)) {
            errorMessage.append("Nhân viên phải đủ 18 tuổi!\n");
        }

        if (phoneField.getText() == null || phoneField.getText().trim().isEmpty()) {
            errorMessage.append("Số điện thoại không được trống!\n");
        }
        if (addressField.getText() == null || addressField.getText().trim().isEmpty()) {
            errorMessage.append("Địa chỉ không được trống!\n");
        }
        if (positionComboBox.getValue() == null) {
            errorMessage.append("Vui lòng chọn chức vụ!\n");
        }
        if (salaryField.getText() == null || salaryField.getText().trim().isEmpty()) {
            errorMessage.append("Lương không được trống!\n");
        } else {
            try {
                double salary = Double.parseDouble(salaryField.getText().trim());
                if (salary <= 0) {
                    errorMessage.append("Lương phải lớn hơn 0!\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Lương phải là số hợp lệ!\n");
            }
        }

        if (errorMessage.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}
