package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;
import java.sql.Connection;
import java.util.List;

public class EmployeeBUS {
    private EmployeeDAO employeeDAO;

    public EmployeeBUS(Connection connection) {
        this.employeeDAO = new EmployeeDAO(connection);
    }

    // Lấy danh sách tất cả nhân viên
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    // Tìm nhân viên theo ID
    public Employee getEmployeeById(int employeeID) {
        return employeeDAO.getEmployeeById(employeeID);
    }

    // Thêm nhân viên mới
    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Dữ liệu nhân viên không hợp lệ.");
            return false;
        }

        // Kiểm tra dữ liệu trước khi thêm vào (Có thể kiểm tra các thông tin như trống, hợp lệ,...)
        if (employee.getFullName().isEmpty() || employee.getPhone().isEmpty() || employee.getEmail().isEmpty()) {
            System.out.println("Thông tin nhân viên không đầy đủ.");
            return false;
        }

        // Gọi DAO để thêm nhân viên
        return employeeDAO.addEmployee(employee);
    }

    // Cập nhật thông tin nhân viên
    public boolean updateEmployee(Employee employee) {
        if (employee == null || employee.getEmployeeID() <= 0) {
            System.out.println("Dữ liệu nhân viên không hợp lệ.");
            return false;
        }

        // Kiểm tra dữ liệu trước khi cập nhật
        if (employee.getFullName().isEmpty() || employee.getPhone().isEmpty() || employee.getEmail().isEmpty()) {
            System.out.println("Thông tin nhân viên không đầy đủ.");
            return false;
        }

        // Gọi DAO để cập nhật thông tin nhân viên
        return employeeDAO.updateEmployee(employee);
    }

    // Xóa nhân viên theo ID
    public boolean deleteEmployee(int employeeID) {
        if (employeeID <= 0) {
            System.out.println("ID nhân viên không hợp lệ.");
            return false;
        }

        // Gọi DAO để xóa nhân viên
        return employeeDAO.deleteEmployee(employeeID);
    }
}
