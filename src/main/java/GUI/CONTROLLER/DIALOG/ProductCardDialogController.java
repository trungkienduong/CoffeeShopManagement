package GUI.CONTROLLER.DIALOG;

import BUS.ProductBUS;
import MODEL.Product;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductCardDialogController {

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productPrice;

    private ProductBUS productBUS = ProductBUS.getInstance();

    /**
     * Cập nhật UI hiển thị theo Product truyền vào
     */
    public void setProduct(Product product) {
        if (product == null) return;

        // Hiển thị tên
        productName.setText(product.getProductName());

        // Hiển thị giá (format VND hoặc số thập phân)
        productPrice.setText(String.format("%,.0f VND", product.getSellPrice().doubleValue()));

        // Hiển thị ảnh (nếu có)
        try {
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                Image img = new Image("file:" + product.getImagePath(), true);
                productImage.setImage(img);
            }
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh sản phẩm: " + e.getMessage());
        }
    }
}
