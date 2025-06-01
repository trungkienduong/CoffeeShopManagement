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
import java.util.Objects;

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
        genderComboBox.setValue(employee.getGender() == Employee.Gender.MALE ? "Male" : "Female");
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
        genderComboBox.getItems().addAll("Male", "Female");

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
    }

    private void loadCurrentEmployee() {
        if (userBUS.getCurrentUser() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Current user is undefined.");
            return;
        }

        currentEmployee = employeeBUS.getEmployeeByUsername(userBUS.getCurrentUser().getUsername());

        if (currentEmployee == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Employee not found.");
            return;
        }

        setEmployee(currentEmployee);
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
            currentEmployee.setGender(genderComboBox.getValue().equals("Male") ? Employee.Gender.MALE : Employee.Gender.FEMALE);
            currentEmployee.setCccd(cccdField.getText().trim());
            currentEmployee.setDateOfBirth(birthDatePicker.getValue());
            currentEmployee.setPhone(phoneField.getText().trim());
            currentEmployee.setAddress(addressField.getText().trim());
            currentEmployee.setPosition(positionComboBox.getValue());
            currentEmployee.setSalary(Double.parseDouble(salaryField.getText().trim()));
            currentEmployee.setImagePath(imagePath);

            if (employeeBUS.updateEmployee(currentEmployee)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee updated successfully.");
                if (dialogStage != null) dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update employee.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        boolean valid = true;

        clearErrorBorder(fullNameField);
        clearErrorBorder(genderComboBox);
        clearErrorBorder(cccdField);
        clearErrorBorder(birthDatePicker);
        clearErrorBorder(phoneField);
        clearErrorBorder(addressField);
        clearErrorBorder(positionComboBox);
        clearErrorBorder(salaryField);

        StringBuilder errorMessage = new StringBuilder();

        if (fullNameField.getText() == null || fullNameField.getText().trim().isEmpty()) {
            errorMessage.append("Full name is required.\n");
            setErrorBorder(fullNameField);
            valid = false;
        }

        if (genderComboBox.getValue() == null) {
            errorMessage.append("Please select a gender.\n");
            setErrorBorder(genderComboBox);
            valid = false;
        }

        String cccd = cccdField.getText();
        if (cccd == null || cccd.trim().isEmpty()) {
            errorMessage.append("CCCD is required.\n");
            setErrorBorder(cccdField);
            valid = false;
        } else if (!cccd.trim().matches("\\d{12}")) {
            errorMessage.append("Citizen ID must be exactly 12 digits.\n");
            setErrorBorder(cccdField);
            valid = false;
        }

        LocalDate birthDate = birthDatePicker.getValue();
        LocalDate minDate = LocalDate.now().minusYears(18);
        if (birthDate == null || birthDate.isAfter(minDate)) {
            errorMessage.append("Employee must be at least 18 years old.\n");
            setErrorBorder(birthDatePicker);
            valid = false;
        }

        String phone = phoneField.getText();
        if (phone == null || phone.trim().isEmpty()) {
            errorMessage.append("Phone number is required.\n");
            setErrorBorder(phoneField);
            valid = false;
        } else if (!phone.trim().matches("\\d{10}")) {
            errorMessage.append("Phone number must be exactly 10 digits.\n");
            setErrorBorder(phoneField);
            valid = false;
        }

        if (addressField.getText() == null || addressField.getText().trim().isEmpty()) {
            errorMessage.append("Address is required.\n");
            setErrorBorder(addressField);
            valid = false;
        }

        if (positionComboBox.getValue() == null) {
            errorMessage.append("Please select a position.\n");
            setErrorBorder(positionComboBox);
            valid = false;
        }

        String salaryText = salaryField.getText();
        if (salaryText == null || salaryText.trim().isEmpty()) {
            errorMessage.append("Salary is required.\n");
            setErrorBorder(salaryField);
            valid = false;
        } else {
            try {
                double salary = Double.parseDouble(salaryText.trim());
                if (salary <= 0) {
                    errorMessage.append("Salary must be greater than 0.\n");
                    setErrorBorder(salaryField);
                    valid = false;
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Salary must be a valid number.\n");
                setErrorBorder(salaryField);
                valid = false;
            }
        }

        if (!valid) {
            showAlert(Alert.AlertType.ERROR, "Error", errorMessage.toString());
        }

        return valid;
    }

    private void setErrorBorder(Control control) {
        control.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    private void clearErrorBorder(Control control) {
        control.setStyle("");
    }


    private void showAlert(Alert.AlertType type, String message, String string) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        String css = Objects.requireNonNull(getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css")).toExternalForm();
        dialogPane.getStylesheets().add(css);

        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}