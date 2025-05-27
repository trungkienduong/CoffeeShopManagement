package GUI.CONTROLLER.DIALOG;

import MODEL.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EmployeeCardDialogController {

    @FXML
    private ImageView employeeImage;

    @FXML
    private Label employeeName;

    @FXML
    private Label employeeRole;

    /**
     * Cập nhật UI hiển thị theo Employee truyền vào
     */
    public void setEmployee(Employee employee) {
        if (employee == null) return;

        // Hiển thị tên nhân viên
        employeeName.setText(employee.getFullName());

        // Hiển thị chức vụ, nếu có
        if (employee.getPosition() != null) {
            employeeRole.setText(employee.getPosition().getPositionName());
        } else {
            employeeRole.setText("");
        }

        // Hiển thị ảnh (nếu có)
        try {
            if (employee.getImagePath() != null && !employee.getImagePath().isEmpty()) {
                Image img = new Image("file:" + employee.getImagePath(), true);
                employeeImage.setImage(img);
            }
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh nhân viên: " + e.getMessage());
        }
    }
}
