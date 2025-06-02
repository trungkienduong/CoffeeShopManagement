package GUI.CONTROLLER.PANEL;

import BUS.EmployeeBUS;
import BUS.UserBUS;
import GUI.CONTROLLER.DIALOG.AddEmployeeDialogController;
import GUI.CONTROLLER.DIALOG.EditEmployeeDialogController;
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
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select an employee to delete.", null);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete the selected employee?");
        DialogPane confirmPane = confirmAlert.getDialogPane();
        String css = Objects.requireNonNull(getClass().getResource("/ASSETS/STYLES/DIALOG/alert.css")).toExternalForm();
        confirmPane.getStylesheets().add(css);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                EmployeeBUS.getInstance().deleteEmployee(selectedEmployee.getEmployeeId());

                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee deleted successfully.", null);
                selectedEmployee = null;

                currentUserEmployee = EmployeeBUS.getInstance().getEmployeeByUsername(Session.currentUsername);
                updateAddButtonState();

                loadEmployees();
                applyRolePermissions();

            } catch (IllegalStateException ex) {
                showAlert(Alert.AlertType.ERROR, "Delete Failed", "Cannot delete employee because they are currently in use.", null);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Delete Failed", "Failed to delete employee due to an unexpected error.", null);
                ex.printStackTrace();
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

        List<Employee> filtered = EmployeeBUS.getInstance().searchEmployees(keyword);
        for (Employee emp : filtered) {
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

    private void openDialog(String fxmlPath, String title, DialogControllerHandler handler) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle(title);
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);

            Object controller = loader.getController();

            if (controller instanceof AddEmployeeDialogController) {
                ((AddEmployeeDialogController) controller).setDialogStage(dialogStage);
            } else if (controller instanceof EditEmployeeDialogController) {
                ((EditEmployeeDialogController) controller).setDialogStage(dialogStage);
            }


            handler.handle(controller);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showAlert(Alert.AlertType type, String title, String message, Object o) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
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
