package GUI.CONTROLLER.PANEL;

import BUS.ProductBUS;
import BUS.UserBUS;
import MODEL.Product;
import MODEL.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    private Product selectedProduct = null;
    private User currentUser;

    @FXML
    private void initialize() {
        currentUser = UserBUS.getInstance().getCurrentUser();
        loadProducts();
        applyRolePermissions();
    }

    private void applyRolePermissions() {
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

    public void loadProducts() {
        loadProducts(null);
    }

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

    public void handleSearch(String keyword) {
        loadProducts(keyword);
        selectedProduct = null;
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
            showAlert(Alert.AlertType.WARNING, "Warning", "No product selected to edit!");
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
            showAlert(Alert.AlertType.WARNING, "Warning", "No product selected to delete!");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete the selected product?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        boolean success = ProductBUS.getInstance().deleteProduct(selectedProduct.getProductId());
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product deleted successfully.");
            selectedProduct = null;
            loadProducts();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete product. It might be in use.");
        }
    }

    @FXML
    private void handleViewProduct(ActionEvent event) {
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No product selected to view!");
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

    private void showAlert(Alert.AlertType type, String message, String s) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        String css = getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css").toExternalForm();
        dialogPane.getStylesheets().add(css);

        alert.showAndWait();
    }
}
