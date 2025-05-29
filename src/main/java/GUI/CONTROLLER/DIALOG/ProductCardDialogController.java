package GUI.CONTROLLER.DIALOG;

import MODEL.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductCardDialogController {

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productPrice;

    public void setProduct(Product product) {
        if (product == null) return;

        productName.setText(product.getProductName());
        productPrice.setText(String.format("%,.0f", product.getSellPrice().doubleValue()));

        loadProductImage(product.getImagePath());
    }

    private void loadProductImage(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                Image img = new Image("file:" + imagePath, true);
                productImage.setImage(img);
            } else {
                setDefaultImage();
            }
        } catch (Exception e) {
            System.out.println("⚠ Failed to load image: " + e.getMessage());
            setDefaultImage();
        }
    }

    private void setDefaultImage() {
        try {
            Image defaultImg = new Image(getClass().getResource("/assets/default_product.png").toExternalForm());
            productImage.setImage(defaultImg);
        } catch (Exception e) {
            System.out.println("⚠ Failed to load default image: " + e.getMessage());
        }
    }
}
