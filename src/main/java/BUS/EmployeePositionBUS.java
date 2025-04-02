package BUS;

import DAO.EmployeePositionDAO;
import MODEL.EmployeePosition;
import java.sql.Connection;
import java.util.List;

public class EmployeePositionBUS {
    private EmployeePositionDAO employeePositionDAO;

    public EmployeePositionBUS(Connection connection) {
        this.employeePositionDAO = new EmployeePositionDAO(connection);
    }

    // Lấy tất cả các chức vụ
    public List<EmployeePosition> getAllEmployeePositions() {
        return employeePositionDAO.getAllEmployeePositions();
    }

    // Tìm chức vụ theo ID
    public EmployeePosition getEmployeePositionByID(int positionID) {
        return employeePositionDAO.getEmployeePositionByID(positionID);
    }

    // Thêm một chức vụ mới
    public boolean addEmployeePosition(EmployeePosition employeePosition) {
        if (employeePosition == null || employeePosition.getPositionName().isEmpty()) {
            System.out.println("Tên chức vụ không hợp lệ.");
            return false;
        }
        return employeePositionDAO.addEmployeePosition(employeePosition);
    }

    // Cập nhật chức vụ
    public boolean updateEmployeePosition(EmployeePosition employeePosition) {
        if (employeePosition == null || employeePosition.getPositionID() <= 0 || employeePosition.getPositionName().isEmpty()) {
            System.out.println("Dữ liệu chức vụ không hợp lệ.");
            return false;
        }
        return employeePositionDAO.updateEmployeePosition(employeePosition);
    }

    // Xóa một chức vụ
    public boolean deleteEmployeePosition(int positionID) {
        if (positionID <= 0) {
            System.out.println("ID chức vụ không hợp lệ.");
            return false;
        }
        return employeePositionDAO.deleteEmployeePosition(positionID);
    }
}
