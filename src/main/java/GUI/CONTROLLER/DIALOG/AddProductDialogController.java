package GUI.CONTROLLER.DIALOG;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AddProductDialogController {

    @FXML
    private ImageView productImageView; // ImageView to show the selected image
    @FXML
    private Button imageButton; // Button to open file chooser for image selection
    @FXML
    private TextField productNameField; // TextField to input product name
    @FXML
    private TextField priceField; // TextField to input product price
    @FXML
    private TextArea descriptionArea; // TextArea to input product description
    @FXML
    private Button confirmButton; // Confirm button to add the product
    @FXML
    private Button cancelButton; // Cancel button to close the dialog

    // Handle the action when the user clicks on the "Xác nhận" (Confirm) button
    @FXML
    private void onConfirm(ActionEvent event) {
        String productName = productNameField.getText();
        String priceText = priceField.getText();
        String description = descriptionArea.getText();

        if (productName.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin sản phẩm.", AlertType.ERROR);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Giá sản phẩm phải là một số hợp lệ.", AlertType.ERROR);
            return;
        }

        // Assuming you have a Product class or some service to handle product creation
        // Example:
        // Product newProduct = new Product(productName, price, description, productImageView.getImage());
        // productService.addProduct(newProduct);

        // Show success alert
        showAlert("Thành công", "Sản phẩm đã được thêm.", AlertType.INFORMATION);

        // Close the dialog
        closeDialog();
    }

    // Handle the action when the user clicks on the "Hủy" (Cancel) button
    @FXML
    private void onCancel(ActionEvent event) {
        closeDialog();
    }

    // Handle the action when the user clicks the image button to choose an image
    @FXML
    private void onChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                productImageView.setImage(image);
            } catch (IOException e) {
                showAlert("Lỗi", "Không thể tải ảnh. Vui lòng thử lại.", AlertType.ERROR);
            }
        }
    }

    // Utility method to show an alert dialog
    private void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Utility method to close the dialog
    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
