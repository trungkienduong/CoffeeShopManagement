package GUI.CONTROLLER.PANEL;

import BUS.ProductBUS;
import MODEL.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ProductPanelController {

    @FXML
    private FlowPane productContainer;

    @FXML
    private Button addBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button viewBtn;

    private Product selectedProduct = null; // giữ sản phẩm đang chọn (bạn cần bổ sung xử lý chọn)

    @FXML
    private void initialize() {
        loadProducts();
    }

    /**
     * Load danh sách sản phẩm, tạo card và thêm vào container
     */
    private void loadProducts() {
        productContainer.getChildren().clear();

        List<Product> products = ProductBUS.getInstance().getAllProducts();

        for (Product product : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/ProductCard.fxml"));
                Parent productCard = loader.load();

                GUI.CONTROLLER.DIALOG.ProductCardDialogController cardController = loader.getController();
                cardController.setProduct(product);

                // Xử lý chọn card, highlight và gán selectedProduct
                productCard.setOnMouseClicked(e -> {
                    selectedProduct = product;

                    // Remove highlight các card khác
                    productContainer.getChildren().forEach(node -> node.getStyleClass().remove("selected-card"));
                    // Highlight card được chọn
                    productCard.getStyleClass().add("selected-card");
                });

                productContainer.getChildren().add(productCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddProductDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add New Product");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            // Sau khi dialog đóng, refresh lại danh sách
            loadProducts();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProduct(ActionEvent event) {
        if (selectedProduct == null) {
            System.out.println("Chưa chọn sản phẩm để sửa!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EditProductDialog.fxml"));
            Parent root = loader.load();

            // Truyền sản phẩm đã chọn sang dialog sửa (nếu cần)
            GUI.CONTROLLER.DIALOG.EditProductDialogController controller = loader.getController();
            controller.setProduct(selectedProduct);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Product");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            loadProducts();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Chưa chọn sản phẩm để xóa!");
            alert.showAndWait();
            return;
        }

        boolean success = ProductBUS.getInstance().deleteProduct(selectedProduct.getProductId());
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Xóa sản phẩm thành công.");
            alert.showAndWait();

            selectedProduct = null;
            loadProducts();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Xóa sản phẩm thất bại. Có thể sản phẩm đang được sử dụng.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleViewProduct(ActionEvent event) {
        if (selectedProduct == null) {
            System.out.println("Chưa chọn sản phẩm để xem!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/ViewProductDialog.fxml"));
            Parent root = loader.load();

            GUI.CONTROLLER.DIALOG.ViewProductDialogController controller = loader.getController();
            controller.setProduct(selectedProduct);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("View Product");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
