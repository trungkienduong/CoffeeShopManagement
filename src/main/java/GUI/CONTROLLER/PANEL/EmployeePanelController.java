package GUI.CONTROLLER.PANEL;

import BUS.EmployeeBUS;
import BUS.UserBUS;
import MODEL.Employee;
import MODEL.User;
import UTIL.Session;
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
    private Employee currentAccountEmployee = null; // Nhân viên tương ứng tài khoản đăng nhập
    private User currentUser = null;                // Người dùng đang đăng nhập (để kiểm tra role)

    @FXML
    private void initialize() {
        // Lấy user hiện tại (đã đăng nhập)
        currentUser = UserBUS.getInstance().getCurrentUser();

        loadEmployees();

        // Lấy lại nhân viên ứng với tài khoản đăng nhập (nếu có)
        currentAccountEmployee = EmployeeBUS.getInstance().getEmployeeByUsername(Session.currentUsername);

        // Phân quyền nút add, delete dựa trên role
        applyRolePermissions();

        // Nếu muốn có thêm logic disable nút edit/view khi chưa chọn nhân viên thì cũng có thể làm ở đây

        checkAddButtonVisibility();

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

                // Xử lý click chọn card
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

    private void applyRolePermissions() {
        if (currentUser == null) {
            // Không đăng nhập thì tắt hết
            addBtn.setDisable(true);
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
            viewBtn.setDisable(true);
            return;
        }

        if (currentUser.hasRole("STAFF")) {
            // STAFF được phép add, delete
            addBtn.setDisable(false);
            deleteBtn.setDisable(false);
        } else if (currentUser.hasRole("EMPLOYEE")) {
            // EMPLOYEE không được add, không được delete
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        } else {
            // Các role khác (vd ADMIN) mặc định bật hết
            addBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }
    }

    private void checkAddButtonVisibility() {
        // Nếu đã có employee gắn với tài khoản này thì disable nút Add
        currentAccountEmployee = EmployeeBUS.getInstance().getEmployeeByUsername(Session.currentUsername);
        addBtn.setDisable(currentAccountEmployee != null);
    }

    @FXML
    private void handleAddEmployee(ActionEvent event) {
        if (currentAccountEmployee != null) {
            showWarning("Bạn đã thêm thông tin cá nhân rồi.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Thêm Thông Tin Cá Nhân");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);

            // Lấy controller
            GUI.CONTROLLER.DIALOG.AddEmployeeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Mở dialog
            dialogStage.showAndWait();

            // Cập nhật danh sách nhân viên và trạng thái
            loadEmployees(); // sẽ gán lại currentAccountEmployee trong đó
            checkAddButtonVisibility();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleEditEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showWarning("Bạn chưa chọn nhân viên để sửa.");
            return;
        }

        // Phân quyền edit: EMPLOYEE chỉ được sửa chính mình
        if (currentUser.hasRole("EMPLOYEE")) {
            if (!selectedEmployee.getUsername().equals(Session.currentUsername)) {
                showWarning("Bạn chỉ được sửa thông tin của chính mình.");
                return;
            }
        }
        // STAFF được sửa tất cả

        openEditEmployeeDialog(selectedEmployee);
    }

    @FXML
    private void handleDeleteEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showWarning("Chưa chọn nhân viên để xoá!");
            return;
        }

        if (!currentUser.hasRole("STAFF")) {
            showWarning("Bạn không có quyền xoá nhân viên.");
            return;
        }

        boolean success = EmployeeBUS.getInstance().deleteEmployee(selectedEmployee.getEmployeeId());

        if (success) {
            showInfo("Xoá nhân viên thành công.");
            selectedEmployee = null;
            loadEmployees();
            applyRolePermissions();
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

        // EMPLOYEE chỉ xem được chính mình
        if (currentUser.hasRole("EMPLOYEE")) {
            if (!selectedEmployee.getUsername().equals(Session.currentUsername)) {
                showWarning("Bạn chỉ được xem thông tin của chính mình.");
                return;
            }
        }
        // STAFF được xem tất cả

        openViewEmployeeDialog(selectedEmployee);
    }

    private void openEditEmployeeDialog(Employee emp) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EditEmployeeDialog.fxml"));
            Parent root = loader.load();

            GUI.CONTROLLER.DIALOG.EditEmployeeDialogController controller = loader.getController();
            controller.setEmployee(emp);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Sửa Thông Tin Nhân Viên");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);

            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            loadEmployees();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openViewEmployeeDialog(Employee emp) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/ViewEmployeeDialog.fxml"));
            Parent root = loader.load();

            GUI.CONTROLLER.DIALOG.ViewEmployeeDialogController controller = loader.getController();
            controller.setEmployee(emp);

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
