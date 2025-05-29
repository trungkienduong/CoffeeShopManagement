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
        employeeBUS = EmployeeBUS.getInstance();
        positionBUS = EmployeePositionBUS.getInstance();
        userBUS = UserBUS.getInstance();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        try {
            if (userBUS.getCurrentUser() != null) {
                emailField.setText(userBUS.getCurrentUser().getEmail());
            } else {
                emailField.setText("Unknown");
            }
            emailField.setDisable(true);

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

            birthDatePicker.setValue(LocalDate.now().minusYears(18));
            birthDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null) birthDatePicker.setValue(LocalDate.now().minusYears(18));
            });

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot initialize form: " + e.getMessage());
        }
    }

    @FXML
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
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot load image: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        if (!validateInput()) return;
        try {
            Employee employee = new Employee();
            employee.setUsername(userBUS.getCurrentUser().getUsername());
            employee.setFullName(fullNameField.getText().trim());
            employee.setGender(Employee.Gender.fromCode(genderComboBox.getValue().equals("Male") ? 'M' : 'F'));
            employee.setCccd(cccdField.getText().trim());
            employee.setDateOfBirth(birthDatePicker.getValue());
            employee.setPhone(phoneField.getText().trim());
            employee.setAddress(addressField.getText().trim());
            employee.setPosition(positionComboBox.getValue());
            employee.setSalary(Double.parseDouble(salaryField.getText().trim()));
            employee.setImagePath(imagePath);
            employee.setJoinDate(LocalDate.now());

            if (employeeBUS.addEmployee(employee)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee added successfully!");
                if (dialogStage != null) dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add employee. Please try again!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Salary must be a valid number!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        if (dialogStage != null) dialogStage.close();
    }

    private boolean validateInput() {
        boolean valid = true;
        StringBuilder errorMessage = new StringBuilder();

        clearErrorStyles();

        if (fullNameField.getText() == null || fullNameField.getText().trim().isEmpty()) {
            errorMessage.append("- Full name cannot be empty!\n");
            fullNameField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        }

        if (genderComboBox.getValue() == null) {
            errorMessage.append("- Please select gender!\n");
            genderComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        }

        String cccd = cccdField.getText();
        if (cccd == null || !cccd.matches("\\d{12}")) {
            errorMessage.append("- Citizen ID must be exactly 12 digits!\n");
            cccdField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        }

        LocalDate birthDate = birthDatePicker.getValue();
        LocalDate minDate = LocalDate.now().minusYears(18);
        if (birthDate == null || birthDate.isAfter(minDate)) {
            errorMessage.append("- Employee must be at least 18 years old!\n");
            birthDatePicker.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        }

        String phone = phoneField.getText();
        if (phone == null || !phone.matches("\\d{10}")) {
            errorMessage.append("- Phone number must be exactly 10 digits!\n");
            phoneField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        }

        if (addressField.getText() == null || addressField.getText().trim().isEmpty()) {
            errorMessage.append("- Address cannot be empty!\n");
            addressField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        }

        if (positionComboBox.getValue() == null) {
            errorMessage.append("- Please select a position!\n");
            positionComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        }

        String salaryText = salaryField.getText();
        if (salaryText == null || salaryText.trim().isEmpty()) {
            errorMessage.append("- Salary cannot be empty!\n");
            salaryField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            valid = false;
        } else {
            try {
                double salary = Double.parseDouble(salaryText.trim());
                if (salary <= 0) {
                    errorMessage.append("- Salary must be greater than 0!\n");
                    salaryField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                errorMessage.append("- Salary must be a valid number!\n");
                salaryField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                valid = false;
            }
        }

        if (!valid) {
            showAlert(Alert.AlertType.ERROR, "Validation Errors", errorMessage.toString());
        }

        return valid;
    }


    private void clearErrorStyles() {
        fullNameField.setStyle("");
        genderComboBox.setStyle("");
        cccdField.setStyle("");
        birthDatePicker.setStyle("");
        phoneField.setStyle("");
        addressField.setStyle("");
        positionComboBox.setStyle("");
        salaryField.setStyle("");
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
}
