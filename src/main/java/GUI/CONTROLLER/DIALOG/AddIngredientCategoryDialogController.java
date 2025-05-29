package GUI.CONTROLLER.DIALOG;

import BUS.IngredientCategoryBUS;
import MODEL.IngredientCategory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Objects;

public class AddIngredientCategoryDialogController {

    @FXML
    private TextField IngredientCategoryNameField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private IngredientCategoryBUS categoryBUS;

    @FXML
    private void initialize() {
        categoryBUS = IngredientCategoryBUS.getInstance();

        confirmButton.setOnAction(event -> onConfirm());
        cancelButton.setOnAction(event -> onCancel());
    }

    private void onConfirm() {
        String categoryName = IngredientCategoryNameField.getText();

        if (categoryName == null || categoryName.trim().isEmpty()) {
            showAlert(Alert.AlertType.valueOf("Error"), "Ingredient category name cannot be empty.");
            return;
        }

        IngredientCategory category = new IngredientCategory();
        category.setIngredientCategoryName(categoryName.trim());

        boolean success = categoryBUS.addCategory(category);

        if (success) {
            showAlert(Alert.AlertType.valueOf("Success"), "New ingredient category added successfully!");
            closeDialog();
        } else {
            showAlert(Alert.AlertType.valueOf("Error"), "Ingredient category name already exists or an error occurred.");
        }
    }

    private void onCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
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
}
