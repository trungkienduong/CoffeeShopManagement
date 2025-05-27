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
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.DateCell;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    // Lưu Inventory hiện tại (nếu có) để biết là sửa hay thêm mới
    private Inventory currentInventory = null;

    @FXML
    public void initialize() {
        loadIngredientCategories();
        loadSuppliers();
        loadUnitCategories();
        loadEmployees();

        importDatePicker.setValue(LocalDate.now());
        // Không cho chọn ngày tương lai
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

        // Tính tổng tiền realtime
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

            // Reload dữ liệu sau khi thêm mới
            if (clickedButton == addItemButton) loadIngredientCategories();
            else if (clickedButton == addUnitButton) loadUnitCategories();
            else if (clickedButton == addSupplierButton) loadSuppliers();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở cửa sổ", e.getMessage());
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

    // Method mới để load Inventory hiện tại lên form khi sửa
    public void setInventory(Inventory inventory) {
        this.currentInventory = inventory;

        if (inventory != null) {
            // Chọn mặt hàng (IngredientCategory)
            if (inventory.getIngredientCategory() != null) {
                itemComboBox.getSelectionModel().select(inventory.getIngredientCategory());
            } else {
                itemComboBox.getSelectionModel().clearSelection();
            }

            // Chọn đơn vị
            if (inventory.getUnit() != null) {
                unitComboBox.getSelectionModel().select(inventory.getUnit());
            } else {
                unitComboBox.getSelectionModel().clearSelection();
            }

            // Gán số lượng
            if (inventory.getQuantity() != null) {
                quantityField.setText(inventory.getQuantity().toPlainString());
            } else {
                quantityField.clear();
            }

            // Gán đơn giá
            if (inventory.getCostPrice() != null) {
                unitPriceField.setText(inventory.getCostPrice().toPlainString());
            } else {
                unitPriceField.clear();
            }

            // Những phần supplier, employee, importDate, note tạm thời chưa có trong Inventory, có thể clear hoặc để mặc định
            supplierComboBox.getSelectionModel().clearSelection();
            employeeComboBox.getSelectionModel().clearSelection();
            importDatePicker.setValue(LocalDate.now());
            noteArea.clear();

            // Cập nhật lại tổng tiền
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

            if (selectedCategory == null) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng chọn mặt hàng (loại nguyên liệu).");
                return;
            }
            if (selectedUnit == null) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng chọn đơn vị.");
                return;
            }
            if (selectedSupplier == null) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng chọn nhà cung cấp.");
                return;
            }
            if (selectedEmployee == null) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng chọn nhân viên.");
                return;
            }

            String quantityText = quantityField.getText().trim();
            String unitPriceText = unitPriceField.getText().trim();
            LocalDate importDate = importDatePicker.getValue();

            if (quantityText.isEmpty() || unitPriceText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng nhập số lượng và đơn giá.");
                return;
            }

            BigDecimal quantity = new BigDecimal(quantityText);
            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số lượng phải lớn hơn 0.");
                return;
            }

            BigDecimal unitPrice = new BigDecimal(unitPriceText);
            if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Đơn giá không hợp lệ.");
                return;
            }

            if (importDate == null || importDate.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Ngày nhập không hợp lệ.");
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
            boolean success;

            if (currentInventory == null) {
                // Thêm mới phiếu nhập kho
                success = importLogBUS.addImportLog(log);
            } else {
                // Cập nhật phiếu nhập kho - bạn cần bổ sung thêm logic update nếu có ID hoặc cách nhận biết
                success = importLogBUS.updateImportLog(log);
            }

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", null, "Phiếu nhập kho đã được lưu.");
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Lưu phiếu nhập kho thất bại.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số lượng hoặc đơn giá không hợp lệ.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // Hàm tiện ích hiện cảnh báo
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
