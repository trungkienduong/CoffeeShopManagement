package GUI.CONTROLLER.PANEL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeePanelController {

    @FXML
    private FlowPane employeeContainer;

    @FXML
    private Button addBtn;

    // Hàm xử lý khi nhấn nút Add (được gọi từ onAction trong FXML)
    @FXML
    private void handleAddEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Thêm nhân viên mới");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Chặn tương tác với cửa sổ chính
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait(); // Chờ dialog đóng mới tiếp tục
        } catch (IOException e) {
            e.printStackTrace(); // Có thể thay bằng showAlert lỗi nếu muốn
        }
    }

    @FXML
    private void handleViewEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Xem nhân viên");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Chặn tương tác với cửa sổ chính
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait(); // Chờ dialog đóng mới tiếp tục
        } catch (IOException e) {
            e.printStackTrace(); // Có thể thay bằng showAlert lỗi nếu muốn
        }
    }

    @FXML
    private void handleEditEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chiềnh sửa nhân viên");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Chặn tương tác với cửa sổ chính
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait(); // Chờ dialog đóng newcom
        } catch (IOException e) {
            e.printStackTrace(); // Có thể thay bằng showAlert lỗi nếu muốn
        }
    }

    @FXML
    private void handleDeleteEmployee(ActionEvent event) {
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/AddEmployeeDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Xóa nhân viên");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Chặn tương tác với cửa sổ chính
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait(); // Chờ dialog đóng newcom
        } catch (IOException e) {
            e.printStackTrace(); // Có thể thay bằng showAlert lỗi nếu muốn
        }*/
    }
}
