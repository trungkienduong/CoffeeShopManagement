package GUI.CONTROLLER.PANEL;

import BUS.ProductBUS;
import BUS.UserBUS;
import MODEL.Product;
import MODEL.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    private Product selectedProduct = null; // sản phẩm đang chọn
    private User currentUser;               // user hiện tại

    @FXML
    private void initialize() {
        currentUser = UserBUS.getInstance().getCurrentUser(); // lấy user hiện tại 1 lần
        loadProducts();
        handleRolePermission();
    }

    private void handleRolePermission() {
        if (currentUser == null) {
            disableAllButtons();
            return;
        }

        if (currentUser.hasRole("EMPLOYEE")) {
            addBtn.setDisable(true);
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }
    }

    private void disableAllButtons() {
        addBtn.setDisable(true);
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);
        viewBtn.setDisable(true);
    }

    /**
     * Load tất cả sản phẩm (không lọc)
     */
    public void loadProducts() {
        loadProducts(null);
    }

    /**
     * Load sản phẩm và hiển thị trong productContainer
     * Nếu keyword == null hoặc empty thì load tất cả sản phẩm
     * Ngược lại thì lọc theo tên sản phẩm chứa keyword (không phân biệt hoa thường)
     */
    public void loadProducts(String keyword) {
        productContainer.getChildren().clear();

        List<Product> products;
        if (keyword == null || keyword.isEmpty()) {
            products = ProductBUS.getInstance().getAllProducts();
        } else {
            products = ProductBUS.getInstance().searchProductsByName(keyword);
        }

        for (Product product : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/ProductCard.fxml"));
                Parent productCard = loader.load();

                GUI.CONTROLLER.DIALOG.ProductCardDialogController cardController = loader.getController();
                cardController.setProduct(product);

                productCard.setOnMouseClicked(e -> {
                    selectedProduct = product;

                    productContainer.getChildren().forEach(node -> node.getStyleClass().remove("selected-card"));
                    productCard.getStyleClass().add("selected-card");
                });

                productContainer.getChildren().add(productCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hàm này được gọi để tìm kiếm sản phẩm theo tên (từ CoffeeShopGUI hay UI khác gọi)
     */
    public void handleSearch(String keyword) {
        loadProducts(keyword);
        selectedProduct = null; // reset chọn sản phẩm khi tìm kiếm
    }

    @FXML
    private void handleAddProduct(ActionEvent event) {
        if (currentUser != null && currentUser.hasRole("EMPLOYEE")) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddProductDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add New Product");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            loadProducts();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProduct(ActionEvent event) {
        if (currentUser != null && currentUser.hasRole("EMPLOYEE")) return;

        if (selectedProduct == null) {
            System.out.println("Chưa chọn sản phẩm để sửa!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EditProductDialog.fxml"));
            Parent root = loader.load();

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
        if (currentUser != null && currentUser.hasRole("EMPLOYEE")) return;

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
