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

public class AddProductDialogController {

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

    @FXML
    public void initialize() {
        loadCategoriesToComboBox();
        addTypeButton.setOnAction(this::handleAddType);
    }

    private void loadCategoriesToComboBox() {
        List<Category> categoryList = CategoryBUS.getInstance().getAllCategories();
        ObservableList<Category> observableList = FXCollections.observableArrayList(categoryList);
        recipeComboBox.setItems(observableList);

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

    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Product Image");
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
        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String description = descriptionArea.getText().trim();
        Category selectedCategory = recipeComboBox.getValue();

        if (selectedImageFile == null) {
            showAlert("Please select a product image.");
            return;
        }

        if (selectedCategory == null) {
            showAlert("Please select a product category.");
            return;
        }

        if (name.isEmpty()) {
            showAlert("Product name cannot be empty.");
            return;
        }

        if (ProductBUS.getInstance().isProductNameExists(name)) {
            showAlert("Product name already exists.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Invalid product price.");
            return;
        }

        Product product = new Product();
        product.setProductName(name);
        product.setSellPrice(BigDecimal.valueOf(price));
        product.setDescription(description);
        product.setCategoryId(selectedCategory.getCategoryId());
        product.setImagePath(selectedImageFile.getAbsolutePath());

        boolean success = ProductBUS.getInstance().addProduct(product);
        if (success) {
            closeDialog();
        } else {
            showAlert("Failed to add product.");
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
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

            updateRecipeComboBox();

        } catch (IOException e) {
            showErrorAlert("Error", "Cannot open window", e.getMessage());
        }
    }

    private void updateRecipeComboBox() {
        List<Category> categoryList = CategoryBUS.getInstance().getAllCategories();
        ObservableList<Category> observableList = FXCollections.observableArrayList(categoryList);
        recipeComboBox.setItems(observableList);
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
