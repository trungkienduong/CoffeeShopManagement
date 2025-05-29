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
import java.util.Objects;

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

    private void loadCategoriesToComboBox() {
        List<Category> categories = CategoryBUS.getInstance().getAllCategories();
        ObservableList<Category> list = FXCollections.observableArrayList(categories);
        recipeComboBox.setItems(list);

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
            System.out.println("Unable to load image: " + e.getMessage());
        }
    }

    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Product Image");
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
            showAlert(Alert.AlertType.valueOf("Error"), "Invalid product.");
            return;
        }

        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String description = descriptionArea.getText().trim();
        Category selectedCategory = recipeComboBox.getValue();

        if (selectedImageFile == null) {
            showAlert(Alert.AlertType.valueOf("Notice"), "Please select a product image.");
            return;
        }

        if (selectedCategory == null) {
            showAlert(Alert.AlertType.valueOf("Notice"), "Please select a product category.");
            return;
        }

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.valueOf("Notice"), "Product name cannot be empty.");
            return;
        }

        if (!name.equalsIgnoreCase(currentProduct.getProductName()) && ProductBUS.getInstance().isProductNameExists(name)) {
            showAlert(Alert.AlertType.valueOf("Notice"), "Product name already exists.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.valueOf("Notice"), "Invalid product price.");
            return;
        }

        currentProduct.setProductName(name);
        currentProduct.setSellPrice(BigDecimal.valueOf(price));
        currentProduct.setDescription(description);
        currentProduct.setCategoryId(selectedCategory.getCategoryId());
        currentProduct.setImagePath(selectedImageFile.getAbsolutePath());

        boolean success = ProductBUS.getInstance().updateProduct(currentProduct);
        if (success) {
            closeDialog();
        } else {
            showAlert(Alert.AlertType.valueOf("Notice"), "Failed to update product.");
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

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        String css = Objects.requireNonNull(getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css")).toExternalForm();
        dialogPane.getStylesheets().add(css);

        alert.showAndWait();
    }

    private void handleAddType(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddCategoryDialog.fxml"));
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(addTypeButton.getScene().getWindow());
            dialog.setTitle("Add New Product Category");

            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.showAndWait();

            loadCategoriesToComboBox();
        } catch (IOException e) {
            showAlert(Alert.AlertType.valueOf("Error"), "Unable to open category dialog.");
        }
    }
}
