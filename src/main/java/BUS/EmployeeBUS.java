package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;

import java.util.List;

public class EmployeeBUS {
    private final EmployeeDAO employeeDAO;

    // 1. Tạo instance tĩnh duy nhất
    private static EmployeeBUS instance;

    // 2. Constructor private để tránh tạo instance ngoài class
    public EmployeeBUS() {
        this.employeeDAO = EmployeeDAO.getInstance();
    }

    // 3. Phương thức lấy instance duy nhất
    public static EmployeeBUS getInstance() {
        if (instance == null) {
            instance = new EmployeeBUS();
        }
        return instance;
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        return employeeDAO.getEmployeeById(id);
    }

    public boolean addEmployee(Employee emp) {
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
