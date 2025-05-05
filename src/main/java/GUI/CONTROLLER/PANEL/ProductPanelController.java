package GUI.CONTROLLER.PANEL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProductPanelController {

    @FXML
    private FlowPane productContainer;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private AnchorPane rootPane;

    // Khi khởi tạo giao diện
    public void initialize() {
        // Giả lập thêm sản phẩm vào FlowPane
        addMockProduct();

        // Đặt sự kiện cho nút Add Product
        addButton.setOnAction(event -> addMockProduct());

        // Đặt sự kiện cho nút Update
        updateButton.setOnAction(event -> {
            showAlert("Update", "Cập nhật sản phẩm!");
        });

        // Đặt sự kiện cho nút Delete
        deleteButton.setOnAction(event -> {
            showAlert("Delete", "Xóa sản phẩm!");
        });
    }

    // Hàm giả lập thêm sản phẩm vào FlowPane
    private void addMockProduct() {
        // Tạo một product card
        AnchorPane productCard = new AnchorPane();
        productCard.setPrefWidth(150);
        productCard.setPrefHeight(200);

        // Thêm ảnh cho sản phẩm
        Image image = new Image("https://via.placeholder.com/150");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        // Thêm tên sản phẩm
        Text productName = new Text("Sản phẩm mới");
        productName.setLayoutX(10);
        productName.setLayoutY(160);

        // Thêm giá sản phẩm
        Text productPrice = new Text("Giá: 100,000 VNĐ");
        productPrice.setLayoutX(10);
        productPrice.setLayoutY(180);

        // Thêm tất cả vào productCard
        productCard.getChildren().addAll(imageView, productName, productPrice);

        // Thêm card vào FlowPane
        productContainer.getChildren().add(productCard);
    }

    // Hàm hiển thị thông báo (Alert) khi nhấn các nút
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
