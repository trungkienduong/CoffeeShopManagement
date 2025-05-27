package GUI.CONTROLLER.DIALOG;

import BUS.CategoryBUS;
import BUS.ProductBUS;
import MODEL.Category;
import MODEL.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class AddProductDialogController {

    // === FXML ELEMENTS ===
    @FXML private ImageView productImageView;
    @FXML private Button imageButton;

    @FXML private ComboBox<Category> recipeComboBox;
    @FXML private Button addTypeButton;

    @FXML private TextField productNameField;
    @FXML private TextField priceField;
    @FXML private TextArea descriptionArea;

    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    // === VARIABLES ===
    private File selectedImageFile;

    @FXML
    public void initialize() {
        loadCategoriesToComboBox();
        addTypeButton.setOnAction(this::handleAddType);
    }

    private void loadCategoriesToComboBox() {
        List<Category> categoryList = CategoryBUS.getInstance().getAllCategories();
        ObservableList<Category> observableList = FXCollections.observableArrayList(categoryList);
        recipeComboBox.setItems(observableList);

        // Hiển thị tên category
        recipeComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoryName());
            }
        });

        recipeComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoryName());
            }
        });
    }

    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh sản phẩm");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(imageButton.getScene().getWindow());

        if (file != null) {
            selectedImageFile = file;
            productImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void onConfirm() {
        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String description = descriptionArea.getText().trim();
        Category selectedCategory = recipeComboBox.getValue();

        if (selectedImageFile == null) {
            showAlert("Vui lòng chọn ảnh sản phẩm.");
            return;
        }

        if (selectedCategory == null) {
            showAlert("Vui lòng chọn loại sản phẩm.");
            return;
        }

        if (name.isEmpty()) {
            showAlert("Tên sản phẩm không được để trống.");
            return;
        }

        if (ProductBUS.getInstance().isProductNameExists(name)) {
            showAlert("Tên sản phẩm đã tồn tại.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Giá sản phẩm không hợp lệ.");
            return;
        }

        Product product = new Product();
        product.setProductName(name);
        product.setSellPrice(BigDecimal.valueOf(price));
        product.setDescription(description);
        product.setCategoryId(selectedCategory.getCategoryId());
        product.setImagePath(selectedImageFile.getAbsolutePath());

        boolean success = ProductBUS.getInstance().addProduct(product);
        if (success) {
            closeDialog();
        } else {
            showAlert("Thêm sản phẩm thất bại.");
        }
    }

    @FXML
    private void onCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ------------------------ PHẦN MỚI ------------------------

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
        List<Category> categoryList = CategoryBUS.getInstance().getAllCategories();
        ObservableList<Category> observableList = FXCollections.observableArrayList(categoryList);
        recipeComboBox.setItems(observableList);
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
