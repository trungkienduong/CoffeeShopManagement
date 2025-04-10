package BUS;

import DAO.EmployeeDAO;
import MODEL.Employee;

import java.util.List;
import java.util.regex.Pattern;

public class EmployeeBUS {
    private EmployeeDAO employeeDAO;

    public EmployeeBUS() {
        employeeDAO = new EmployeeDAO();
    }

    // Thêm nhân viên sau khi kiểm tra hợp lệ
    public String addEmployee(Employee emp) {
        String error = validateEmployee(emp);
        if (!error.isEmpty()) return error;

        if (isDuplicateCCCD(emp.getCCCD())) {
            return "CCCD đã tồn tại trong hệ thống!";
        }

        boolean success = employeeDAO.addEmployee(emp);
        return success ? "OK" : "Thêm nhân viên thất bại!";
    }

    // Validate dữ liệu nhân viên
    private String validateEmployee(Employee emp) {
        if (emp.getFullName() == null || emp.getFullName().trim().isEmpty()) return "Họ tên không được để trống!";
        if (emp.getCCCD() == null || emp.getCCCD().trim().isEmpty()) return "CCCD không được để trống!";
        if (emp.getPhone() == null || emp.getPhone().trim().isEmpty()) return "Số điện thoại không được để trống!";
        if (emp.getEmail() == null || emp.getEmail().trim().isEmpty()) return "Email không được để trống!";
        if (emp.getAddress() == null || emp.getAddress().trim().isEmpty()) return "Địa chỉ không được để trống!";

        if (!isValidEmail(emp.getEmail())) return "Email không hợp lệ!";
        if (!isValidPhone(emp.getPhone())) return "Số điện thoại không hợp lệ (chỉ gồm số, 9-11 ký tự)!";

        return "";
    }

    // Regex kiểm tra email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    // Regex kiểm tra số điện thoại
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{9,11}");
    }

    // Kiểm tra trùng CCCD trong database
    private boolean isDuplicateCCCD(String cccd) {
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee e : employees) {
            if (e.getCCCD().equals(cccd)) {
                return true;
            }
        }
        return false;
    }

    // Các hàm khác giữ nguyên
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    public String updateEmployee(Employee emp) {
        String error = validateEmployee(emp);
        if (!error.isEmpty()) return error;

        boolean success = employeeDAO.updateEmployee(emp);
        return success ? "OK" : "Cập nhật nhân viên thất bại!";
    }

    public boolean deleteEmployee(int employeeId) {
        return employeeDAO.deleteEmployee(employeeId);
    }

    public List<Employee> searchEmployees(String keyword) {
        return employeeDAO.searchEmployees(keyword);
    }
}
