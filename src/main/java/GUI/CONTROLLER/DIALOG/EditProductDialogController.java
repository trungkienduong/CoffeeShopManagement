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

public class EditProductDialogController {

    @FXML private ImageView productImageView;
    @FXML private Button imageButton;

    @FXML private ComboBox<Category> recipeComboBox;
    @FXML private Button addTypeButton;

    @FXML private TextField productNameField;
    @FXML private TextField priceField;
    @FXML private TextArea descriptionArea;

    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    private File selectedImageFile;
    private Product currentProduct;

    @FXML
    public void initialize() {
        loadCategoriesToComboBox();

        addTypeButton.setOnAction(this::handleAddType);
    }

    // Load danh sách category vào combo box
    private void loadCategoriesToComboBox() {
        List<Category> categories = CategoryBUS.getInstance().getAllCategories();
        ObservableList<Category> list = FXCollections.observableArrayList(categories);
        recipeComboBox.setItems(list);

        // Hiển thị tên category trong combo box
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

    // Gán dữ liệu product hiện tại vào form (để sửa)
    public void setProduct(Product product) {
        this.currentProduct = product;

        productNameField.setText(product.getProductName());
        priceField.setText(product.getSellPrice().toPlainString());
        descriptionArea.setText(product.getDescription());

        Category selectedCat = CategoryBUS.getInstance().getCategoryById(product.getCategoryId());
        recipeComboBox.setValue(selectedCat);

        try {
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                Image img = new Image("file:" + product.getImagePath(), true);
                productImageView.setImage(img);
                selectedImageFile = new File(product.getImagePath());
            }
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh: " + e.getMessage());
        }
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
        if (currentProduct == null) {
            showAlert("Lỗi", "Sản phẩm không hợp lệ.");
            return;
        }

        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String description = descriptionArea.getText().trim();
        Category selectedCategory = recipeComboBox.getValue();

        if (selectedImageFile == null) {
            showAlert("Thông báo", "Vui lòng chọn ảnh sản phẩm.");
            return;
        }

        if (selectedCategory == null) {
            showAlert("Thông báo", "Vui lòng chọn loại sản phẩm.");
            return;
        }

        if (name.isEmpty()) {
            showAlert("Thông báo", "Tên sản phẩm không được để trống.");
            return;
        }

        if (!name.equalsIgnoreCase(currentProduct.getProductName()) && ProductBUS.getInstance().isProductNameExists(name)) {
            showAlert("Thông báo", "Tên sản phẩm đã tồn tại.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Thông báo", "Giá sản phẩm không hợp lệ.");
            return;
        }

        // Cập nhật product
        currentProduct.setProductName(name);
        currentProduct.setSellPrice(BigDecimal.valueOf(price));
        currentProduct.setDescription(description);
        currentProduct.setCategoryId(selectedCategory.getCategoryId());
        currentProduct.setImagePath(selectedImageFile.getAbsolutePath());

        boolean success = ProductBUS.getInstance().updateProduct(currentProduct);
        if (success) {
            closeDialog();
        } else {
            showAlert("Thông báo", "Cập nhật sản phẩm thất bại.");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

            // Cập nhật lại danh sách category sau khi thêm
            loadCategoriesToComboBox();

        } catch (IOException e) {
            showAlert("Lỗi", "Không thể mở cửa sổ thêm loại sản phẩm.");
        }
    }
}
