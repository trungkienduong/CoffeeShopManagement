package GUI.CONTROLLER.DIALOG;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductDialogController {

    @FXML
    private ImageView productImageView;

    @FXML
    private Button imageButton;

    @FXML
    private ComboBox<String> recipeComboBox;

    @FXML
    private Button addTypeButton;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        // Xử lý sự kiện cho nút thêm loại sản phẩm
        addTypeButton.setOnAction(this::handleAddType);

        // Xử lý sự kiện cho các nút khác
        confirmButton.setOnAction(this::onConfirm);
        cancelButton.setOnAction(this::onCancel);
        imageButton.setOnAction(this::onChooseImage);
    }

    @FXML
    private void handleAddType(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddCategoryDialog.fxml"));
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(addTypeButton.getScene().getWindow());
            dialog.setTitle("Thêm loại sản phẩm mới");

            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.showAndWait();

            // Sau khi dialog đóng, cập nhật lại dữ liệu cho ComboBox
            updateRecipeComboBox();

        } catch (IOException e) {
            showErrorAlert("Lỗi", "Không thể mở cửa sổ", e.getMessage());
        }
    }

    private void updateRecipeComboBox() {
        // TODO: Cập nhật dữ liệu cho recipeComboBox từ database
    }

    @FXML
    private void onChooseImage(ActionEvent event) {
        // TODO: Xử lý chọn ảnh
    }

    @FXML
    private void onConfirm(ActionEvent event) {
        // TODO: Xử lý xác nhận
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}