package GUI.CONTROLLER.DIALOG;

import BUS.CategoryBUS;
import BUS.InventoryBUS;
import BUS.ProductRecipeBUS;
import BUS.UnitCategoryBUS;
import MODEL.Category;
import MODEL.Inventory;
import MODEL.ProductRecipe;
import MODEL.UnitCategory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class AddCategoryDialogController implements Initializable {

    @FXML
    private TextField categoryNameField;

    @FXML
    private ComboBox<Inventory> item1ComboBox, item2ComboBox, item3ComboBox;
    @FXML
    private TextField quantity1Field, quantity2Field, quantity3Field;
    @FXML
    private ComboBox<UnitCategory> unit1ComboBox, unit2ComboBox, unit3ComboBox;

    @FXML
    private Button confirmButton, cancelButton;

    private final InventoryBUS inventoryBUS = InventoryBUS.getInstance();
    private final UnitCategoryBUS unitBUS = UnitCategoryBUS.getInstance();
    private final CategoryBUS categoryBUS = CategoryBUS.getInstance();
    private final ProductRecipeBUS productRecipeBUS = ProductRecipeBUS.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadInventoryItems();
        loadUnits();
        confirmButton.setOnAction(event -> handleConfirm());
        cancelButton.setOnAction(event -> handleCancel());
    }

    private void loadInventoryItems() {
        List<Inventory> items = inventoryBUS.getAll();
        if (items != null) {
            item1ComboBox.setItems(FXCollections.observableArrayList(items));
            item2ComboBox.setItems(FXCollections.observableArrayList(items));
            item3ComboBox.setItems(FXCollections.observableArrayList(items));
        }
    }

    private void loadUnits() {
        List<UnitCategory> units = unitBUS.getAll();
        if (units != null) {
            unit1ComboBox.setItems(FXCollections.observableArrayList(units));
            unit2ComboBox.setItems(FXCollections.observableArrayList(units));
            unit3ComboBox.setItems(FXCollections.observableArrayList(units));
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void handleConfirm() {
        String categoryName = categoryNameField.getText().trim();
        if (categoryName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Category name cannot be empty.");
            return;
        }

        Category category = new Category();
        category.setCategoryName(categoryName);
        if (!categoryBUS.insertCategory(category)) {
            showAlert(Alert.AlertType.ERROR, "Failed to add category. The name might already exist.");
            return;
        }

        Category inserted = categoryBUS.findByName(categoryName);
        if (inserted == null) {
            showAlert(Alert.AlertType.ERROR, "Error retrieving newly added category info.");
            return;
        }

        List<ProductRecipe> recipeList = new ArrayList<>();
        boolean hasValidRecipe = false;

        recipeList.add(getRecipeFromRow(inserted, item1ComboBox, quantity1Field));
        recipeList.add(getRecipeFromRow(inserted, item2ComboBox, quantity2Field));
        recipeList.add(getRecipeFromRow(inserted, item3ComboBox, quantity3Field));

        for (ProductRecipe recipe : recipeList) {
            if (recipe != null) {
                hasValidRecipe = true;
                if (!productRecipeBUS.addProductRecipe(recipe)) {
                    showAlert(Alert.AlertType.ERROR, "Error adding ingredient recipe: " + recipe.getItem().getItemName());
                    return;
                }
            }
        }

        if (!hasValidRecipe) {
            showAlert(Alert.AlertType.WARNING, "At least one ingredient must be in the recipe.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Category and recipe added successfully.");
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    private ProductRecipe getRecipeFromRow(Category category, ComboBox<Inventory> itemBox, TextField quantityField) {
        Inventory item = itemBox.getValue();
        String quantityText = quantityField.getText().trim();

        if (item == null || quantityText.isEmpty()) return null;

        try {
            BigDecimal quantity = new BigDecimal(quantityText);
            if (quantity.compareTo(BigDecimal.ZERO) <= 0) return null;

            ProductRecipe recipe = new ProductRecipe();
            recipe.setCategory(category);
            recipe.setItem(item);
            recipe.setQuantityUsed(quantity);
            return recipe;

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
