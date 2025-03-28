package DAO;

import MODEL.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Lấy danh sách tất cả nhân viên
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM EMPLOYEE";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("EMPLOYEE_ID"),
                        rs.getString("FULLNAME"),
                        rs.getString("POSITION"),
                        rs.getBigDecimal("SALARY"),
                        rs.getString("PHONE"),
                        rs.getString("ADDRESS")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
        return employees;
    }

    // Thêm nhân viên mới
    public boolean addEmployee(Employee emp) {
        String query = "INSERT INTO EMPLOYEE (FULLNAME, POSITION, SALARY, PHONE, ADDRESS) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, emp.getFullName());
            pstmt.setString(2, emp.getPosition());
            pstmt.setBigDecimal(3, emp.getSalary());
            pstmt.setString(4, emp.getPhone());
            pstmt.setString(5, emp.getAddress());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin nhân viên
    public boolean updateEmployee(Employee emp) {
        String query = "UPDATE EMPLOYEE SET FULLNAME=?, POSITION=?, SALARY=?, PHONE=?, ADDRESS=? WHERE EMPLOYEE_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, emp.getFullName());
            pstmt.setString(2, emp.getPosition());
            pstmt.setBigDecimal(3, emp.getSalary());
            pstmt.setString(4, emp.getPhone());
            pstmt.setString(5, emp.getAddress());
            pstmt.setInt(6, emp.getEmployeeID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
            return false;
        }
    }

    // Xóa nhân viên theo ID
    public boolean deleteEmployee(int employeeId) {
        String query = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, employeeId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }

    // Tìm nhân viên theo ID, họ tên, số điện thoại hoặc chức vụ
    public List<Employee> searchEmployees(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_ID LIKE ? OR FULLNAME LIKE ? OR PHONE LIKE ? OR POSITION LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern); // Tìm theo EMPLOYEE_ID
            pstmt.setString(2, searchPattern); // Tìm theo FULLNAME
            pstmt.setString(3, searchPattern); // Tìm theo PHONE
            pstmt.setString(4, searchPattern); // Tìm theo POSITION

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("EMPLOYEE_ID"),
                        rs.getString("FULLNAME"),
                        rs.getString("POSITION"),
                        rs.getBigDecimal("SALARY"),
                        rs.getString("PHONE"),
                        rs.getString("ADDRESS")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
        return employees;
    }
}
