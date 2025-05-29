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

    public void setEmployee(Employee employee) {
        if (employee == null) return;

        employeeName.setText(employee.getFullName());

        if (employee.getPosition() != null) {
            employeeRole.setText(employee.getPosition().getPositionName());
        } else {
            employeeRole.setText("");
        }

        try {
            if (employee.getImagePath() != null && !employee.getImagePath().isEmpty()) {
                Image img = new Image("file:" + employee.getImagePath(), true);
                employeeImage.setImage(img);
            }
        } catch (Exception e) {
            System.out.println("Unable to load employee image: " + e.getMessage());
        }
    }
}
