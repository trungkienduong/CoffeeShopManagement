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

    public void setProduct(Product product) {
        if (product == null) return;

        productName.setText(product.getProductName());
        productPrice.setText(String.format("%,.0f VND", product.getSellPrice().doubleValue()));

        try {
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                Image img = new Image("file:" + product.getImagePath(), true);
                productImage.setImage(img);
            }
        } catch (Exception e) {
            System.out.println("Failed to load product image: " + e.getMessage());
        }
    }
}
