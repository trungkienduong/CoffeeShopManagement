package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;

import java.util.List;

public class EmployeeBUS {
    private final EmployeeDAO employeeDAO;

    public EmployeeBUS() {
        this.employeeDAO = EmployeeDAO.getInstance();
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        return employeeDAO.getEmployeeById(id);
    }

    public boolean addEmployee(Employee emp) {
        // Kiểm tra hợp lệ nếu cần (ví dụ: CCCD đúng định dạng, không null các trường bắt buộc)
        if (emp == null || emp.getUsername() == null || emp.getFullName() == null) {
            return false;
        }

        return employeeDAO.insertEmployee(emp);
    }

    public boolean updateEmployee(Employee emp) {
        if (emp == null || emp.getEmployeeId() == 0) {
            return false;
        }

        return employeeDAO.updateEmployee(emp);
    }

    public boolean deleteEmployee(int id) {
        return employeeDAO.deleteEmployee(id);
    }

    public List<Employee> searchEmployees(String keyword) {
        // Gợi ý: nếu bạn muốn mở rộng, có thể viết thêm method search trong DAO.
        List<Employee> all = getAllEmployees();
        return all.stream()
                .filter(e ->
                        e.getFullName().toLowerCase().contains(keyword.toLowerCase()) ||
                                e.getUsername().toLowerCase().contains(keyword.toLowerCase()) ||
                                e.getPhone().contains(keyword) ||
                                e.getCccd().contains(keyword)
                )
                .toList();
    }
}
