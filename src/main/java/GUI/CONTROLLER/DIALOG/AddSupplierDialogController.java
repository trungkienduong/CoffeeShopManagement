package GUI.CONTROLLER.DIALOG;

import BUS.SupplierBUS;
import MODEL.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

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
        confirmButton.setOnAction(event -> {
            if (validateInput()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierName(supplierNameField.getText().trim());
                supplier.setPhone(phoneField.getText().trim());
                supplier.setEmail(emailField.getText().trim());
                supplier.setAddress(addressArea.getText().trim());

                boolean success = supplierBUS.addSupplier(supplier);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "New supplier has been added.");
                    closeDialog();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add supplier. Please check again.");
                }
            }
        });

        cancelButton.setOnAction(event -> closeDialog());
    }

    private boolean validateInput() {
        String name = supplierNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        boolean valid = true;

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please enter the supplier name.");
            valid = false;
        }

        if (!phone.matches("\\d{10}")) {
            phoneField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            showAlert(Alert.AlertType.WARNING, "Invalid Data", "Phone number must be exactly 10 digits.");
            valid = false;
        } else {
            phoneField.setStyle("");
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            emailField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            showAlert(Alert.AlertType.WARNING, "Invalid Data", "Email is invalid.");
            valid = false;
        } else {
            emailField.setStyle("");
        }

        return valid;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        String css = Objects.requireNonNull(getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css")).toExternalForm();
        dialogPane.getStylesheets().add(css);

        alert.showAndWait();
    }

    private void closeDialog() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
