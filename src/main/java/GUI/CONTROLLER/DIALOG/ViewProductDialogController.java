package GUI.CONTROLLER.DIALOG;

import BUS.CategoryBUS;
import BUS.ProductBUS;
import MODEL.Category;
import MODEL.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class ViewProductDialogController {

    @FXML
    private ImageView productImageView;

    @FXML
    private Label productTypeLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button closeButton;

    private Product currentProduct;

    // Hàm này sẽ được gọi từ bên ngoài (controller gọi dialog) để truyền sản phẩm cần xem
    public void setProduct(Product product) {
        this.currentProduct = product;
        if (product != null) {
            loadProductDetails();
        }
    }

    private void loadProductDetails() {
        // Hiển thị tên loại sản phẩm
        Category category = CategoryBUS.getInstance().getCategoryById(currentProduct.getCategoryId());
        if (category != null) {
            productTypeLabel.setText(category.getCategoryName());
        } else {
            productTypeLabel.setText("Không rõ");
        }

        // Hiển thị tên, giá, mô tả
        productNameLabel.setText(currentProduct.getProductName());
        priceLabel.setText(currentProduct.getSellPrice().toPlainString());
        descriptionLabel.setText(currentProduct.getDescription() != null ? currentProduct.getDescription() : "");

        // Hiển thị ảnh (nếu có)
        if (currentProduct.getImagePath() != null && !currentProduct.getImagePath().isEmpty()) {
            File imageFile = new File(currentProduct.getImagePath());
            if (imageFile.exists()) {
                productImageView.setImage(new Image(imageFile.toURI().toString()));
            } else {
                // Xử lý trường hợp file ảnh không tồn tại (để trống hoặc ảnh mặc định)
                productImageView.setImage(null);
            }
        } else {
            productImageView.setImage(null);
        }
    }

    @FXML
    private void handleClose() {
        // Đóng dialog
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
