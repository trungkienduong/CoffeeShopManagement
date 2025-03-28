package DAO;

import MODEL.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Connection connection;

    // Constructor khởi tạo kết nối cơ sở dữ liệu
    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức thêm nhân viên mới
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO EMPLOYEE (FullName, Gender, CCCD, DateOfBirth, Phone, Email, Address, PositionId, Salary, JoinDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getGender());
            stmt.setString(3, employee.getCCCD());
            stmt.setDate(4, new java.sql.Date(employee.getDateOfBirth().getTime()));
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getEmail());
            stmt.setString(7, employee.getAddress());
            stmt.setInt(8, employee.getPositionID());
            stmt.setBigDecimal(9, employee.getSalary());
            stmt.setDate(10, new java.sql.Date(employee.getJoinDate().getTime()));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức lấy danh sách tất cả nhân viên
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM EMPLOYEE";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getString("CCCD"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getInt("PositionId"),
                        rs.getBigDecimal("Salary"),
                        rs.getDate("JoinDate")
                );
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    // Phương thức lấy thông tin nhân viên theo EmployeeID
    public Employee getEmployeeById(int employeeID) {
        String sql = "SELECT * FROM EMPLOYEE WHERE EmployeeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("EmployeeID"),
                            rs.getString("FullName"),
                            rs.getString("Gender"),
                            rs.getString("CCCD"),
                            rs.getDate("DateOfBirth"),
                            rs.getString("Phone"),
                            rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getInt("PositionId"),
                            rs.getBigDecimal("Salary"),
                            rs.getDate("JoinDate")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức cập nhật thông tin nhân viên
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE EMPLOYEE SET FullName = ?, Gender = ?, CCCD = ?, DateOfBirth = ?, Phone = ?, Email = ?, " +
                "Address = ?, PositionId = ?, Salary = ?, JoinDate = ? WHERE EmployeeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getGender());
            stmt.setString(3, employee.getCCCD());
            stmt.setDate(4, new java.sql.Date(employee.getDateOfBirth().getTime()));
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getEmail());
            stmt.setString(7, employee.getAddress());
            stmt.setInt(8, employee.getPositionID());
            stmt.setBigDecimal(9, employee.getSalary());
            stmt.setDate(10, new java.sql.Date(employee.getJoinDate().getTime()));
            stmt.setInt(11, employee.getEmployeeID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức xóa nhân viên theo EmployeeID
    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM EMPLOYEE WHERE EmployeeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
