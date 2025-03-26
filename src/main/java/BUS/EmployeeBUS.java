package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;
import java.util.List;

public class EmployeeBUS {
    private EmployeeDAO employeeDAO;

    public EmployeeBUS() {
        this.employeeDAO = new EmployeeDAO();
    }

    // Lấy danh sách tất cả nhân viên
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    // Thêm nhân viên mới
    public boolean addEmployee(Employee employee) {
        return employeeDAO.addEmployee(employee);
    }

    // Cập nhật thông tin nhân viên
    public boolean updateEmployee(Employee employee) {
        return employeeDAO.updateEmployee(employee);
    }

    // Xóa nhân viên theo ID
    public boolean deleteEmployee(int employeeId) {
        return employeeDAO.deleteEmployee(employeeId);
    }

    // Tìm kiếm nhân viên theo tên hoặc chức vụ
    public List<Employee> searchEmployees(String keyword) {
        return employeeDAO.searchEmployees(keyword);
    }
}
