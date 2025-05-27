package GUI.CONTROLLER.PANEL;

import BUS.EmployeeBUS;
import MODEL.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EmployeePanelController {

    @FXML
    private FlowPane employeeContainer;

    @FXML
    private Button addBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button viewBtn;

    private Employee selectedEmployee = null;

    @FXML
    private void initialize() {
        loadEmployees();
    }

    private void loadEmployees() {
        employeeContainer.getChildren().clear();
        List<Employee> employees = EmployeeBUS.getInstance().getAllEmployees();

        for (Employee emp : employees) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EmployeeCard.fxml"));
                Parent card = loader.load();

                // Gán dữ liệu cho card
                GUI.CONTROLLER.DIALOG.EmployeeCardDialogController controller = loader.getController();
                controller.setEmployee(emp);

                // Gán xử lý khi click card
                card.setOnMouseClicked(e -> {
                    selectedEmployee = emp;

                    // Bỏ highlight các card khác
                    employeeContainer.getChildren().forEach(node -> node.getStyleClass().remove("selected-card"));
                    // Highlight card được chọn
                    card.getStyleClass().add("selected-card");
                });

                employeeContainer.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleAddEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Thêm Nhân Viên");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            loadEmployees();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showWarning("Chưa chọn nhân viên để sửa!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EditEmployeeDialog.fxml"));
            Parent root = loader.load();

            GUI.CONTROLLER.DIALOG.EditEmployeeDialogController controller = loader.getController();
            controller.setEmployee(selectedEmployee);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Sửa Nhân Viên");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            loadEmployees();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showWarning("Chưa chọn nhân viên để xoá!");
            return;
        }

        boolean success = EmployeeBUS.getInstance().deleteEmployee(selectedEmployee.getEmployeeId());

        if (success) {
            showInfo("Xoá nhân viên thành công.");
            selectedEmployee = null;
            loadEmployees();
        } else {
            showError("Xoá nhân viên thất bại. Có thể nhân viên đang được sử dụng.");
        }
    }

    @FXML
    private void handleViewEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showWarning("Chưa chọn nhân viên để xem!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/ViewEmployeeDialog.fxml"));
            Parent root = loader.load();

            GUI.CONTROLLER.DIALOG.ViewEmployeeDialogController controller = loader.getController();
            controller.setEmployee(selectedEmployee);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Thông Tin Nhân Viên");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ================== Helper ====================

    private void showWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
