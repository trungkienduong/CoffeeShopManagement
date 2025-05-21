package GUI.CONTROLLER.DIALOG;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryUpdateDialogController {

    @FXML
    private Button addItemButton, addUnitButton, addSupplierButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    void handleIconButtonClick(ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();
            String fxmlPath = "";

            if (clickedButton == addItemButton) {
                fxmlPath = "/FXML/DIALOG/AddInventoryItemDialog.fxml";
            } else if (clickedButton == addUnitButton) {
                fxmlPath = "/FXML/DIALOG/AddUnitCategoryDialog.fxml";
            } else if (clickedButton == addSupplierButton) {
                fxmlPath = "/FXML/DIALOG/AddSupplierDialog.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(addItemButton.getScene().getWindow());

            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể mở cửa sổ");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}