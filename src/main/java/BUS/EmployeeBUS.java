package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;

import java.util.List;

public class EmployeeBUS {
    private static EmployeeBUS instance;
    private EmployeeDAO employeeDAO;

    private EmployeeBUS() {
        employeeDAO = EmployeeDAO.getInstance();
    }

    public static EmployeeBUS getInstance() {
        if (instance == null) {
            instance = new EmployeeBUS();
        }
        return instance;
    }

    //---------------------- INSERT ----------------------
    public boolean insertEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Invalid employee data.");
            return false;
        }

        // Kiểm tra trùng username
        if (employeeDAO.findByName(employee.getUsername()) != null) {
            System.out.println("Username already exists.");
            return false;
        }

        // Kiểm tra các trường bắt buộc
        if (employee.getFullname() == null || employee.getFullname().isEmpty()) {
            System.out.println("Full name is required.");
            return false;
        }

        if (employee.getCccd() == null || employee.getCccd().isEmpty()) {
            System.out.println("CCCD is required.");
            return false;
        }

        if (employee.getPhone() == null || employee.getPhone().isEmpty()) {
            System.out.println("Phone number is required.");
            return false;
        }

        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            System.out.println("Address is required.");
            return false;
        }

        // Kiểm tra lương hợp lệ
        if (employee.getSalary() < 0) {
            System.out.println("Salary must be greater than or equal to 0.");
            return false;
        }

        // Thực hiện insert vào DB
        return employeeDAO.insert(employee);
    }

    //---------------------- UPDATE ----------------------
    public boolean updateEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Invalid employee data.");
            return false;
        }

        // Kiểm tra thông tin bắt buộc
        if (employee.getFullname() == null || employee.getFullname().isEmpty()) {
            System.out.println("Full name is required.");
            return false;
        }

        if (employee.getCccd() == null || employee.getCccd().isEmpty()) {
            System.out.println("CCCD is required.");
            return false;
        }

        if (employee.getPhone() == null || employee.getPhone().isEmpty()) {
            System.out.println("Phone number is required.");
            return false;
        }

        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            System.out.println("Address is required.");
            return false;
        }

        // Kiểm tra lương hợp lệ
        if (employee.getSalary() < 0) {
            System.out.println("Salary must be greater than or equal to 0.");
            return false;
        }

        // Thực hiện update vào DB
        return employeeDAO.update(employee);
    }

    //---------------------- DELETE ----------------------
    public boolean deleteEmployee(int employeeId) {
        return employeeDAO.delete(employeeId);
    }

    //---------------------- GET ALL ----------------------
    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    //---------------------- FIND BY NAME ----------------------
    public Employee FindByName(String username) {
        return employeeDAO.findByName(username);
    }

    //---------------------- FIND BY ID ----------------------
    public Employee FindById(int employeeId) {
        return employeeDAO.findById(employeeId);
    }

    //---------------------- SEARCH BY NAME ----------------------
    public List<Employee> searchByName(String keyword) {
        return employeeDAO.searchByName(keyword);
    }
}
