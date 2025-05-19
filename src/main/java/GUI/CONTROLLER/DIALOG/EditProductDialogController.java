package GUI.CONTROLLER.DIALOG;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.util.Arrays;

public class EditProductDialogController {

    @FXML
    private ImageView productImageView;

    @FXML
    private Button imageButton, confirmButton, cancelButton;

    @FXML
    private ComboBox<String> recipeComboBox;

    @FXML
    private TextField productNameField, priceField;

    @FXML
    private TextArea descriptionArea;

    private File selectedImageFile = null;

    // Giả lập dữ liệu sản phẩm từ database
    private final String mockProductName = "Cà phê sữa";
    private final String mockPrice = "25000";
    private final String mockDescription = "Thơm ngon, béo nhẹ, phù hợp với mọi người.";
    private final String mockRecipeType = "Cà phê";
/*
    private final String mockImagePath = "file:src/ASSETS/IMAGES/sample.jpg"; // đường dẫn mẫu ảnh
*/

    @FXML
    public void initialize() {
        // Load loại sản phẩm (giả lập)
        recipeComboBox.getItems().addAll("Cà phê", "Trà sữa", "Sinh tố", "Khác");

        // Set dữ liệu ban đầu từ database
        recipeComboBox.setValue(mockRecipeType);
        productNameField.setText(mockProductName);
        priceField.setText(mockPrice);
        descriptionArea.setText(mockDescription);
/*
        productImageView.setImage(new Image(mockImagePath));
*/
    }

    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh sản phẩm");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg")
        );
        Window stage = imageButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedImageFile = file;
            productImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void onConfirm() {
        String name = productNameField.getText();
        String price = priceField.getText();
        String description = descriptionArea.getText();
        String recipe = recipeComboBox.getValue();

        System.out.println("== Thông tin sản phẩm mới ==");
        System.out.println("Tên: " + name);
        System.out.println("Giá: " + price);
        System.out.println("Loại: " + recipe);
        System.out.println("Mô tả: " + description);
        System.out.println("Ảnh: " + (selectedImageFile != null ? selectedImageFile.getAbsolutePath() : "Không đổi"));

        // Sau này bạn gọi BUS.updateProduct(...) ở đây
    }

    @FXML
    private void onCancel() {
        // Đóng dialog, nếu dùng Stage riêng
        cancelButton.getScene().getWindow().hide();
    }
}
