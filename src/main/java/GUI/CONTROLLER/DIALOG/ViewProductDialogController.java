package GUI.CONTROLLER.DIALOG;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

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

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
        // Gán dữ liệu tạm để test
        productTypeLabel.setText("Cà phê");
        productNameLabel.setText("Cà phê sữa đá");
        priceLabel.setText("25,000");
        descriptionLabel.setText("Thức uống truyền thống, đậm đà, dễ gây nghiện 😄");

        // Load ảnh mẫu từ internet hoặc local (nếu bạn có ảnh trong resources thì dùng getResource)
        try {
            Image image = new Image("https://internetviettel.vn/wp-content/uploads/2017/05/1-2.jpg", true);
            productImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh: " + e.getMessage());
        }
    }
}
