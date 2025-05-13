package GUI.CONTROLLER.DIALOG;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

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

    // Nút xác nhận
    @FXML
    private void onConfirm(ActionEvent event) {
        String fullname = fullnameField.getText();
        String gender = genderComboBox.getValue();
        String cccd = cccdField.getText();
        LocalDate birthDate = birthDatePicker.getValue();
        String phone = phoneField.getText();
        String address = addressArea.getText();
        String salaryText = salaryField.getText();

        if (fullname.isEmpty() || gender == null || cccd.isEmpty() || birthDate == null ||
                phone.isEmpty() || address.isEmpty() || salaryText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin nhân viên.", Alert.AlertType.ERROR);
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Lương phải là một số hợp lệ.", Alert.AlertType.ERROR);
            return;
        }

        // TODO: Gọi BUS để thêm nhân viên vào database tại đây
        // Employee newEmployee = new Employee(...);
        // employeeBUS.addEmployee(newEmployee);

        showAlert("Thành công", "Nhân viên đã được thêm.", Alert.AlertType.INFORMATION);
        closeDialog();
    }

    // Nút hủy
    @FXML
    private void onCancel(ActionEvent event) {
        closeDialog();
    }

    // Nút chọn ảnh
    @FXML
    private void onChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                employeeImageView.setImage(image);
            } catch (IOException e) {
                showAlert("Lỗi", "Không thể tải ảnh. Vui lòng thử lại.", Alert.AlertType.ERROR);
            }
        }
    }

    // Hàm tiện ích hiển thị thông báo
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Hàm tiện ích đóng dialog
    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
