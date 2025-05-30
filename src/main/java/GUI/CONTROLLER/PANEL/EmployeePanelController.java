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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EmployeePanelController {

    @FXML private FlowPane employeeContainer;
    @FXML private Button addBtn, editBtn, deleteBtn, viewBtn;

    private List<Employee> allEmployees;
    private Employee selectedEmployee;
    private Employee currentUserEmployee;
    private User currentUser;

    @FXML
    private void initialize() {
        currentUser = UserBUS.getInstance().getCurrentUser();
        currentUserEmployee = EmployeeBUS.getInstance().getEmployeeByUsername(Session.currentUsername);

        loadEmployees();
        applyRolePermissions();
        updateAddButtonState();
    }

    private void loadEmployees() {
        employeeContainer.getChildren().clear();
        allEmployees = EmployeeBUS.getInstance().getAllEmployees();

        for (Employee emp : allEmployees) {
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
    }

    private void applyRolePermissions() {
        boolean isStaff = currentUser != null && currentUser.hasRole("STAFF");
        boolean isEmployee = currentUser != null && currentUser.hasRole("EMPLOYEE");

        addBtn.setDisable(!isStaff && !isEmployee);
        deleteBtn.setDisable(!isStaff);
    }

    private void updateAddButtonState() {
        currentUserEmployee = EmployeeBUS.getInstance().getEmployeeByUsername(Session.currentUsername);
        addBtn.setDisable(currentUserEmployee != null);
    }

    @FXML
    private void handleAddEmployee(ActionEvent event) {
        if (currentUserEmployee != null) {
            showAlert(Alert.AlertType.WARNING, "You have already added your personal information.", null, null);
            return;
        }

        openDialog("/FXML/DIALOG/AddEmployeeDialog.fxml", "Add Personal Information", controller -> {
            ((GUI.CONTROLLER.DIALOG.AddEmployeeDialogController) controller).setDialogStage((Stage) addBtn.getScene().getWindow());
        });

        loadEmployees();
        updateAddButtonState();
    }

    @FXML
    private void handleEditEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "Please select an employee to edit.", null, null);
            return;
        }

        if (currentUser.hasRole("EMPLOYEE") && !selectedEmployee.getUsername().equals(Session.currentUsername)) {
            showAlert(Alert.AlertType.WARNING, "You can only edit your own information.", null, null);
            return;
        }

        openDialog("/FXML/DIALOG/EditEmployeeDialog.fxml", "Edit Employee Information", controller -> {
            ((GUI.CONTROLLER.DIALOG.EditEmployeeDialogController) controller).setEmployee(selectedEmployee);
        });

        loadEmployees();
    }

    @FXML
    private void handleDeleteEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "Please select an employee to delete.", null, null);
            return;
        }

        if (!currentUser.hasRole("STAFF")) {
            showAlert(Alert.AlertType.WARNING, "You do not have permission to delete employees.", null, null);
            return;
        }

        if (selectedEmployee.getUsername().equals(currentUser.getUsername())) {
            showAlert(Alert.AlertType.ERROR, "You cannot delete your own account.", null, null);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete the selected employee?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean deleted = EmployeeBUS.getInstance().deleteEmployee(selectedEmployee.getEmployeeId());
            if (deleted) {
                showAlert(Alert.AlertType.INFORMATION, "Employee deleted successfully.", null, null);
                selectedEmployee = null;
                loadEmployees();
                applyRolePermissions();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete employee. The employee might be in use.", null, null);
            }
        }
    }

    @FXML
    private void handleViewEmployee(ActionEvent event) {
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "Please select an employee to view.", null, null);
            return;
        }

        if (currentUser.hasRole("EMPLOYEE") &&
                !selectedEmployee.getUsername().equals(Session.currentUsername)) {
            showAlert(Alert.AlertType.WARNING, "You can only view your own information.", null, null);
            return;
        }

        openDialog("/FXML/DIALOG/ViewEmployeeDialog.fxml", "Employee Information", controller -> {
            ((GUI.CONTROLLER.DIALOG.ViewEmployeeDialogController) controller).setEmployee(selectedEmployee);
        });
    }

    public void handleSearch(String keyword) {
        employeeContainer.getChildren().clear();
        String[] keywords = keyword.trim().toLowerCase().split("\\s+");

        for (Employee emp : allEmployees) {
            String fullText = (emp.getFullName() + " " + (emp.getPosition() != null ? emp.getPosition().getPositionName() : "")).toLowerCase();
            boolean match = true;

            for (String kw : keywords) {
                if (!fullText.contains(kw)) {
                    match = false;
                    break;
                }
            }

            if (match) addEmployeeCard(emp);
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

    private void openDialog(String fxmlPath, String title, DialogControllerHandler handler) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            handler.handle(controller);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle(title);
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message, Object o, String s) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        String css = Objects.requireNonNull(getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css")).toExternalForm();
        dialogPane.getStylesheets().add(css);

        alert.showAndWait();
    }

    @FunctionalInterface
    private interface DialogControllerHandler {
        void handle(Object controller);
    }
}
