package GUI.CONTROLLER.DIALOG;

import MODEL.ImportLog;
import MODEL.Inventory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InventoryViewDialogController {

    @FXML
    private Label itemLabel;

    @FXML
    private Label supplierLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label unitPriceLabel;

    @FXML
    private Label importDateLabel;

    @FXML
    private Label employeeLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private Label totalPriceLabel;

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private void initialize() {
        // Nếu muốn có giá trị mặc định, có thể đặt ở đây (hoặc bỏ trống)
    }

    // Hàm gọi để truyền dữ liệu ImportLog vào để hiển thị
    public void setImportLog(ImportLog log) {
        if (log == null) return;

        itemLabel.setText(log.getItemName() != null ? log.getItemName() : "");

        supplierLabel.setText(String.valueOf(log.getSupplierId()));

        quantityLabel.setText(log.getQuantity() != null ? log.getQuantity().toPlainString() : "");

        if (log.getUnitPrice() != null) {
            // Định dạng tiền VND không có chữ VND cuối, chỉ số và dấu phẩy
            totalPriceLabel.setText(currencyFormat.format(log.getQuantity().multiply(log.getUnitPrice())));
            unitPriceLabel.setText(currencyFormat.format(log.getUnitPrice()));
        } else {
            unitPriceLabel.setText("");
            totalPriceLabel.setText("");
        }

        if (log.getImportDate() != null) {
            importDateLabel.setText(log.getImportDate().format(dateFormatter));
        } else {
            importDateLabel.setText("");
        }

        employeeLabel.setText(log.getEmployeeId() != null ? String.valueOf(log.getEmployeeId()) : "");
        noteLabel.setText(log.getNote() != null ? log.getNote() : "");
    }

    @FXML
    private void handleClose() {
        itemLabel.getScene().getWindow().hide();
    }

    public void setInventory(Inventory selected) {
    }
}
