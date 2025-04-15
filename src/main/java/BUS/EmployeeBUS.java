package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;
import java.util.List;

public class EmployeeBUS {
    private EmployeeDAO employeeDAO;
    private static EmployeeBUS instance;

    private EmployeeBUS() {
        employeeDAO = EmployeeDAO.getInstance();
    }

    public static EmployeeBUS getInstance() {
        if (instance == null) {
            instance = new EmployeeBUS();
        }
        return instance;
    }

    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    public boolean insert(Employee employee) {
        return employeeDAO.insert(employee);
    }

    public boolean update(Employee employee) {
        return employeeDAO.update(employee);
    }

    public boolean delete(int employeeId) {
        return employeeDAO.delete(employeeId);
    }

    public Employee findByUsername(String username) {
        return employeeDAO.findByName(username);
    }

    // Nếu muốn tìm theo email cần bổ sung ở EmployeeDAO
}
