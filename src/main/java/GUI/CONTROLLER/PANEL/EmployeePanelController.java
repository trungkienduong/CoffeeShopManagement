package GUI.CONTROLLER.PANEL;

import BUS.EmployeeBUS;
import BUS.UserBUS;
import MODEL.Employee;
import MODEL.User;
import UTIL.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    @FXML private FlowPane employeeContainer;
    @FXML private Button addBtn;
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;
    @FXML private Button viewBtn;

    private Employee selectedEmployee = null;
    private Employee currentAccountEmployee = null;
    private User currentUser = null;

    private List<Employee> allEmployees;

    @FXML
    private void initialize() {
        currentUser = UserBUS.getInstance().getCurrentUser();

        loadEmployees(); // cũng gán allEmployees ở đây
        currentAccountEmployee = EmployeeBUS.getInstance().getEmployeeByUsername(Session.currentUsername);
        applyRolePermissions();
        checkAddButtonVisibility();
    }

    private void loadEmployees() {
        employeeContainer.getChildren().clear();
        allEmployees = EmployeeBUS.getInstance().getAllEmployees();

        for (Employee emp : allEmployees) {
            addEmployeeCard(emp);
        }
    }

    private void addEmployeeCard(Employee emp) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EmployeeCard.fxml"));
            Parent card = loader.load();

            GUI.CONTROLLER.DIALOG.EmployeeCardDialogController controller = loader.getController();
            controller.setEmployee(emp);

            card.setOnMouseClicked(e -> {
                selectedEmployee = emp;
                employeeContainer.getChildren().forEach(node -> node.getStyleClass().remove("selected-card"));
                card.getStyleClass().add("selected-card");
            });

            employeeContainer.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyRolePermissions() {
        if (currentUser == null) {
            addBtn.setDisable(true);
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
            viewBtn.setDisable(true);
            return;
        }

        if (currentUser.hasRole("STAFF")) {
            addBtn.setDisable(false);
            deleteBtn.setDisable(false);
        } else if (currentUser.hasRole("EMPLOYEE")) {
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        } else {
            addBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }
    }

    private void checkAddButtonVisibility() {
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

            GUI.CONTROLLER.DIALOG.AddEmployeeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            loadEmployees();
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

        if (currentUser.hasRole("EMPLOYEE") &&
                !selectedEmployee.getUsername().equals(Session.currentUsername)) {
            showWarning("Bạn chỉ được sửa thông tin của chính mình.");
            return;
        }

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

        if (currentUser.hasRole("EMPLOYEE") &&
                !selectedEmployee.getUsername().equals(Session.currentUsername)) {
            showWarning("Bạn chỉ được xem thông tin của chính mình.");
            return;
        }

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

    // ============ SEARCH =============
    public void handleSearch(String keyword) {
        employeeContainer.getChildren().clear();

        String[] keywords = keyword.trim().toLowerCase().split("\\s+");

        for (Employee emp : allEmployees) {
            String fullName = emp.getFullName() != null ? emp.getFullName().toLowerCase() : "";
            String positionName = emp.getPosition() != null && emp.getPosition().getPositionName() != null
                    ? emp.getPosition().getPositionName().toLowerCase() : "";

            String fullText = fullName + " " + positionName;

            boolean match = true;
            for (String kw : keywords) {
                if (!fullText.contains(kw)) {
                    match = false;
                    break;
                }
            }

            if (match) {
                addEmployeeCard(emp);
            }
        }
    }




    // ========== Helper ===========
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
