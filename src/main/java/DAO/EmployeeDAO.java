package DAO;

import MODEL.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class EmployeeDAO {

    // Thêm nhân viên
    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO EMPLOYEE (FULLNAME, GENDER, CCCD, DATE_OF_BIRTH, PHONE, EMAIL, ADDRESS, POSITION_ID, SALARY, JOIN_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getFullName());
            stmt.setString(2, emp.getGender());
            stmt.setString(3, emp.getCCCD());
            stmt.setDate(4, new java.sql.Date(emp.getDate_Of_Birth().getTime()));
            stmt.setString(5, emp.getPhone());
            stmt.setString(6, emp.getEmail());
            stmt.setString(7, emp.getAddress());
            stmt.setInt(8, emp.getPosition_ID());
            stmt.setBigDecimal(9, emp.getSalary());
            stmt.setDate(10, new java.sql.Date(emp.getJoinDate().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
            return false;
        }
    }

    // Lấy tất cả nhân viên
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM EMPLOYEE";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractEmployee(rs));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }

        return list;
    }

    // Cập nhật thông tin nhân viên
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE EMPLOYEE SET FULLNAME = ?, GENDER = ?, CCCD = ?, DATE_OF_BIRTH = ?, " +
                "PHONE = ?, EMAIL = ?, ADDRESS = ?, POSITION_ID = ?, SALARY = ?, JOIN_DATE = ? WHERE EMPLOYEE_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getFullName());
            stmt.setString(2, emp.getGender());
            stmt.setString(3, emp.getCCCD());
            stmt.setDate(4, new java.sql.Date(emp.getDate_Of_Birth().getTime()));
            stmt.setString(5, emp.getPhone());
            stmt.setString(6, emp.getEmail());
            stmt.setString(7, emp.getAddress());
            stmt.setInt(8, emp.getPosition_ID());
            stmt.setBigDecimal(9, emp.getSalary());
            stmt.setDate(10, new java.sql.Date(emp.getJoinDate().getTime()));
            stmt.setInt(11, emp.getEmployee_ID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
            return false;
        }
    }

    // Xóa nhân viên
    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm nhân viên theo nhiều thuộc tính (một cách tương đối)
    public List<Employee> searchEmployees(String keyword) {
        List<Employee> result = new ArrayList<>();
        String sql = "SELECT * FROM EMPLOYEE WHERE " +
                "FULLNAME LIKE ? OR CCCD LIKE ? OR PHONE LIKE ? OR EMAIL LIKE ? OR ADDRESS LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String wildcard = "%" + keyword + "%";
            for (int i = 1; i <= 5; i++) {
                stmt.setString(i, wildcard);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(extractEmployee(rs));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }

        return result;
    }

    // Hàm hỗ trợ để tách Employee từ ResultSet
    private Employee extractEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("EMPLOYEE_ID"),
                rs.getString("FULLNAME"),
                rs.getString("GENDER"),
                rs.getString("CCCD"),
                rs.getDate("DATE_OF_BIRTH"),
                rs.getString("PHONE"),
                rs.getString("EMAIL"),
                rs.getString("ADDRESS"),
                rs.getInt("POSITION_ID"),
                rs.getBigDecimal("SALARY"),
                rs.getDate("JOIN_DATE")
        );
    }
}
