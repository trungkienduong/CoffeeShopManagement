package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;

import java.util.List;

public class EmployeeBUS {
    private final EmployeeDAO employeeDAO;

    private static EmployeeBUS instance;

    public EmployeeBUS() {
        this.employeeDAO = EmployeeDAO.getInstance();
    }

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

    public Employee getEmployeeByUsername(String username) {
        return employeeDAO.getByUsername(username);
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
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEmployees();
        }
        String lowerKeyword = keyword.toLowerCase();
        return getAllEmployees().stream()
                .filter(e ->
                        e.getFullName().toLowerCase().contains(lowerKeyword) ||
                                e.getUsername().toLowerCase().contains(lowerKeyword) ||
                                (e.getPhone() != null && e.getPhone().contains(keyword)) ||
                                (e.getCccd() != null && e.getCccd().contains(keyword))
                )
                .toList();
    }

}
