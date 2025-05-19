package GUI.CONTROLLER.DIALOG;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

    @FXML
    private void initialize() {
        // Gán dữ liệu giả lập
        itemLabel.setText("Cà phê sữa đá");
        supplierLabel.setText("Công ty TNHH Cung Ứng Nguyên Liệu ABC");
        quantityLabel.setText("100");
        unitPriceLabel.setText("12,000");
        importDateLabel.setText("18/05/2025");
        employeeLabel.setText("Trần Thị B");
        noteLabel.setText("Nhập bổ sung do tồn kho dưới mức quy định.");
        totalPriceLabel.setText("1,200,000");
    }
}
