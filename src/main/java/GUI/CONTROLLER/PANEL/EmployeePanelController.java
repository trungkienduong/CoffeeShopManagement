package GUI.CONTROLLER.PANEL;

import BUS.EmployeeBUS;
import GUI.CONTROLLER.DIALOG.EmployeeCardDialogController;
import MODEL.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeePanelController implements Initializable {

    @FXML
    private FlowPane employeeContainer;

    @FXML
    private Button addBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEmployeeCards();
    }

    private void loadEmployeeCards() {
        employeeContainer.getChildren().clear();

        List<Employee> employees = EmployeeBUS.getInstance().getAllEmployees();

        for (Employee emp : employees) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EmployeeCard.fxml"));
                Parent card = loader.load();

                // Set thông tin nhân viên cho card
                EmployeeCardDialogController controller = loader.getController();
                controller.setEmployee(emp);

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
            dialogStage.setTitle("Thêm nhân viên mới");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));

            GUI.CONTROLLER.DIALOG.AddEmployeeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            loadEmployeeCards(); // reload sau khi thêm
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/ViewEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Xem nhân viên");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/EditEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chỉnh sửa nhân viên");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));

            GUI.CONTROLLER.DIALOG.EditEmployeeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            loadEmployeeCards(); // reload sau khi sửa
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEmployee(ActionEvent event) {
        // Viết sau nếu muốn dialog xác nhận xoá.
    }
}
