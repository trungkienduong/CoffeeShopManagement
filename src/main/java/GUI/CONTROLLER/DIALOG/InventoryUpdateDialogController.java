package GUI.CONTROLLER.DIALOG;

import BUS.ImportLogBUS;
import BUS.IngredientCategoryBUS;
import BUS.SupplierBUS;
import BUS.UnitCategoryBUS;
import BUS.EmployeeBUS;

import MODEL.ImportLog;
import MODEL.IngredientCategory;
import MODEL.Supplier;
import MODEL.UnitCategory;
import MODEL.Employee;
import MODEL.Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.DateCell;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InventoryUpdateDialogController {

    @FXML
    private Button addItemButton, addUnitButton, addSupplierButton;

    @FXML
    private ComboBox<IngredientCategory> itemComboBox;

    @FXML
    private ComboBox<UnitCategory> unitComboBox;

    @FXML
    private ComboBox<Supplier> supplierComboBox;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField unitPriceField;

    @FXML
    private DatePicker importDatePicker;

    @FXML
    private ComboBox<Employee> employeeComboBox;

    @FXML
    private TextArea noteArea;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button confirmButton, cancelButton;

    @FXML
    private AnchorPane rootPane;

    private Inventory currentInventory = null;

    @FXML
    public void initialize() {
        loadIngredientCategories();
        loadSuppliers();
        loadUnitCategories();
        loadEmployees();

        importDatePicker.setValue(LocalDate.now());
        importDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        quantityField.textProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
        unitPriceField.textProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
    }

    @FXML
    void handleIconButtonClick(ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();
            String fxmlPath = "";

            if (clickedButton == addItemButton) {
                fxmlPath = "/FXML/DIALOG/AddIngredientCategoryDialog.fxml";
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

            if (clickedButton == addItemButton) loadIngredientCategories();
            else if (clickedButton == addUnitButton) loadUnitCategories();
            else if (clickedButton == addSupplierButton) loadSuppliers();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading dialog.");
        }
    }

    private void loadIngredientCategories() {
        IngredientCategoryBUS bus = IngredientCategoryBUS.getInstance();
        List<IngredientCategory> categories = bus.getAllCategories();
        ObservableList<IngredientCategory> observableList = FXCollections.observableArrayList(categories);
        itemComboBox.setItems(observableList);

        itemComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(IngredientCategory item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getIngredientCategoryName());
            }
        });

        itemComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(IngredientCategory item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getIngredientCategoryName());
            }
        });
    }

    private void loadSuppliers() {
        SupplierBUS bus = SupplierBUS.getInstance();
        List<Supplier> suppliers = bus.getSupplierList();
        ObservableList<Supplier> observableList = FXCollections.observableArrayList(suppliers);
        supplierComboBox.setItems(observableList);

        supplierComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Supplier item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getSupplierName());
            }
        });

        supplierComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Supplier item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getSupplierName());
            }
        });
    }

    private void loadUnitCategories() {
        UnitCategoryBUS bus = UnitCategoryBUS.getInstance();
        List<UnitCategory> units = bus.getAll();
        ObservableList<UnitCategory> observableList = FXCollections.observableArrayList(units);
        unitComboBox.setItems(observableList);

        unitComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(UnitCategory item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getUnitName());
            }
        });

        unitComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(UnitCategory item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getUnitName());
            }
        });
    }

    private void loadEmployees() {
        EmployeeBUS bus = EmployeeBUS.getInstance();
        List<Employee> employees = bus.getAllEmployees();
        ObservableList<Employee> observableList = FXCollections.observableArrayList(employees);
        employeeComboBox.setItems(observableList);

        employeeComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getFullName());
            }
        });

        employeeComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getFullName());
            }
        });
    }

    private void calculateTotal() {
        try {
            BigDecimal quantity = new BigDecimal(quantityField.getText());
            BigDecimal unitPrice = new BigDecimal(unitPriceField.getText());

            if (quantity.compareTo(BigDecimal.ZERO) >= 0 && unitPrice.compareTo(BigDecimal.ZERO) >= 0) {
                BigDecimal total = quantity.multiply(unitPrice);
                totalPriceLabel.setText(String.format("%,.0f VND", total));
            } else {
                totalPriceLabel.setText("0 VND");
            }
        } catch (NumberFormatException e) {
            totalPriceLabel.setText("0 VND");
        }
    }

    public void setInventory(Inventory inventory) {
        this.currentInventory = inventory;

        if (inventory != null) {
            if (inventory.getIngredientCategory() != null) {
                itemComboBox.getSelectionModel().select(inventory.getIngredientCategory());
            } else {
                itemComboBox.getSelectionModel().clearSelection();
            }

            if (inventory.getUnit() != null) {
                unitComboBox.getSelectionModel().select(inventory.getUnit());
            } else {
                unitComboBox.getSelectionModel().clearSelection();
            }

            if (inventory.getQuantity() != null) {
                quantityField.setText(inventory.getQuantity().toPlainString());
            } else {
                quantityField.clear();
            }

            if (inventory.getCostPrice() != null) {
                unitPriceField.setText(inventory.getCostPrice().toPlainString());
            } else {
                unitPriceField.clear();
            }

            supplierComboBox.getSelectionModel().clearSelection();
            employeeComboBox.getSelectionModel().clearSelection();
            importDatePicker.setValue(LocalDate.now());
            noteArea.clear();
            calculateTotal();
        }
    }

    @FXML
    private void handleConfirm() {
        try {
            IngredientCategory selectedCategory = itemComboBox.getSelectionModel().getSelectedItem();
            UnitCategory selectedUnit = unitComboBox.getSelectionModel().getSelectedItem();
            Supplier selectedSupplier = supplierComboBox.getSelectionModel().getSelectedItem();
            Employee selectedEmployee = employeeComboBox.getSelectionModel().getSelectedItem();
            LocalDate importDate = importDatePicker.getValue();

            if (selectedCategory == null) {
                showAlert(Alert.AlertType.ERROR, "Please select an ingredient.");
                return;
            }
            if (selectedUnit == null) {
                showAlert(Alert.AlertType.ERROR, "Please select a unit.");
                return;
            }
            if (selectedSupplier == null) {
                showAlert(Alert.AlertType.ERROR, "Please select a supplier.");
                return;
            }
            if (selectedEmployee == null) {
                showAlert(Alert.AlertType.ERROR, "Please select an employee.");
                return;
            }

            String quantityText = quantityField.getText().trim();
            String unitPriceText = unitPriceField.getText().trim();

            if (quantityText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please enter quantity.");
                return;
            }

            BigDecimal quantity;
            try {
                quantity = new BigDecimal(quantityText);
                if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Quantity must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Quantity must be a valid number.");
                return;
            }

            if (unitPriceText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please enter unit price.");
                return;
            }

            BigDecimal unitPrice;
            try {
                unitPrice = new BigDecimal(unitPriceText);
                if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
                    showAlert(Alert.AlertType.ERROR, "Unit price cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Unit price must be a valid number.");
                return;
            }

            if (importDate == null) {
                showAlert(Alert.AlertType.ERROR, "Please select an import date.");
                return;
            }
            if (importDate.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Import date cannot be in the future.");
                return;
            }

            ImportLog log = new ImportLog();
            log.setItemName(selectedCategory.getIngredientCategoryName());
            log.setIngredientCategoryId(selectedCategory.getIngredientCategoryId());
            log.setUnitId(selectedUnit.getUnitId());
            log.setSupplierId(selectedSupplier.getSupplierId());
            log.setQuantity(quantity);
            log.setUnitPrice(unitPrice);
            log.setImportDate(importDate);
            log.setEmployeeId(selectedEmployee.getEmployeeId());
            log.setNote(noteArea.getText());

            ImportLogBUS importLogBUS = ImportLogBUS.getInstance();
            boolean success = (currentInventory == null)
                    ? importLogBUS.addImportLog(log)
                    : importLogBUS.updateImportLog(log);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Import log saved successfully.");
                ((Stage) confirmButton.getScene().getWindow()).close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to save import log.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(message);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        String css = Objects.requireNonNull(getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css")).toExternalForm();
        dialogPane.getStylesheets().add(css);
        alert.showAndWait();
    }
}
