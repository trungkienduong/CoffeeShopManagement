package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;

import java.util.List;
import java.util.stream.Collectors;

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

    public void deleteEmployee(int employeeId) throws Exception {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID.");
        }

        if (employeeDAO.isEmployeeUsedInImportLog(employeeId)) {
            throw new IllegalStateException("Cannot delete employee because they are referenced in other records.");
        }

        boolean deleted = employeeDAO.deleteEmployee(employeeId);

        if (!deleted) {
            throw new Exception("Failed to delete employee due to unknown reasons.");
        }
    }



    public List<Employee> searchEmployees(String keyword) {
        keyword = keyword.toLowerCase().trim();
        String[] keywords = keyword.split("\\s+");

        return getAllEmployees().stream().filter(emp -> {
            String genderText = emp.getGender() != null ? emp.getGender().toLowerString() : "";
            String fullText = (
                    emp.getFullName() + " " +
                            (emp.getPosition() != null ? emp.getPosition().getPositionName() : "") + " " +
                            genderText
            ).toLowerCase();

            for (String kw : keywords) {
                if (!fullText.contains(kw)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }




}
