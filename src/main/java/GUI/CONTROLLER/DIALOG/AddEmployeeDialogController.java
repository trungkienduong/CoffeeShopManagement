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

public class AddEmployeeDialogController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField fullNameField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField cccdField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<EmployeePosition> positionComboBox;
    @FXML
    private TextField salaryField;
    @FXML
    private ImageView employeeImageView;
    @FXML
    private Button browseButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private final EmployeeBUS employeeBUS;
    private final EmployeePositionBUS positionBUS;
    private final UserBUS userBUS;
    private Stage dialogStage;
    private String imagePath;

    public AddEmployeeDialogController() {
        employeeBUS = new EmployeeBUS();
        positionBUS = new EmployeePositionBUS();
        userBUS = UserBUS.getInstance();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        try {
            // Set email
            if (userBUS.getCurrentUser() != null) {
                emailField.setText(userBUS.getCurrentUser().getEmail());
            } else {
                emailField.setText("Không xác định");
            }
            emailField.setDisable(true);

            // Gender
            genderComboBox.getItems().addAll("Nam", "Nữ");

            // Position
            List<EmployeePosition> positions = positionBUS.getAllPositions();
            positionComboBox.getItems().addAll(positions);
            positionComboBox.setConverter(new StringConverter<EmployeePosition>() {
                @Override
                public String toString(EmployeePosition position) {
                    return position != null ? position.getPositionName() : "";
                }

                @Override
                public EmployeePosition fromString(String string) {
                    return null;
                }
            });

            // Buttons
            browseButton.setOnAction(event -> handleBrowse());
            saveButton.setOnAction(event -> handleSave());
            cancelButton.setOnAction(event -> {
                if (dialogStage != null) dialogStage.close();
            });

            // DatePicker mặc định là 18 tuổi
            birthDatePicker.setValue(LocalDate.now().minusYears(18));
            birthDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null) birthDatePicker.setValue(LocalDate.now().minusYears(18));
            });

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể khởi tạo form: " + e.getMessage());
        }
    }

    private void handleBrowse() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(dialogStage);
            if (file != null) {
                imagePath = file.getAbsolutePath();
                Image image = new Image(file.toURI().toString());
                employeeImageView.setImage(image);
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải ảnh: " + e.getMessage());
        }
    }

    private void handleSave() {
        if (!validateInput()) return;
        try {
            Employee employee = new Employee();
            employee.setUsername(userBUS.getCurrentUser().getUsername());
            employee.setFullName(fullNameField.getText().trim());
            employee.setGender(Employee.Gender.fromCode(genderComboBox.getValue().equals("Nam") ? 'M' : 'F'));
            employee.setCccd(cccdField.getText().trim());
            employee.setDateOfBirth(birthDatePicker.getValue());
            employee.setPhone(phoneField.getText().trim());
            employee.setAddress(addressField.getText().trim());
            employee.setPosition(positionComboBox.getValue());
            employee.setSalary(Double.parseDouble(salaryField.getText().trim()));
            employee.setImagePath(imagePath);

            // Gán joinDate bằng ngày hiện tại
            employee.setJoinDate(LocalDate.now());

            if (employeeBUS.addEmployee(employee)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm nhân viên thành công!");
                dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm nhân viên. Vui lòng thử lại!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lương phải là số hợp lệ!");
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
}
