package DAO;

import MODEL.Employee;
import MODEL.EmployeePosition;
import MODEL.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static EmployeeDAO instance;

    private EmployeeDAO() {
        // Singleton constructor
    }

    public static EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT E.*, P.POSITION_NAME FROM EMPLOYEE E " +
                "JOIN EMPLOYEE_POSITION P ON E.POSITION_ID = P.POSITION_ID";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                employees.add(extractEmployee(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeById(int id) {
        String query = "SELECT E.*, P.POSITION_NAME FROM EMPLOYEE E " +
                "JOIN EMPLOYEE_POSITION P ON E.POSITION_ID = P.POSITION_ID " +
                "WHERE E.EMPLOYEE_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractEmployee(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Employee getByUsername(String username) {
        String query = "SELECT E.*, P.POSITION_NAME FROM EMPLOYEE E " +
                "JOIN EMPLOYEE_POSITION P ON E.POSITION_ID = P.POSITION_ID " +
                "WHERE E.USERNAME = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractEmployee(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertEmployee(Employee emp) {
        String query = "INSERT INTO EMPLOYEE (USERNAME, FULLNAME, GENDER, CCCD, DATE_OF_BIRTH, PHONE, ADDRESS, POSITION_ID, SALARY, JOIN_DATE, IMAGE_PATH) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            prepareEmployeeStatement(stmt, emp, false);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateEmployee(Employee emp) {
        String query = "UPDATE EMPLOYEE SET FULLNAME = ?, GENDER = ?, CCCD = ?, DATE_OF_BIRTH = ?, PHONE = ?, ADDRESS = ?, " +
                "POSITION_ID = ?, SALARY = ?, JOIN_DATE = ?, IMAGE_PATH = ? WHERE EMPLOYEE_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            prepareEmployeeStatement(stmt, emp, true);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteEmployee(int employeeId) {
        String query = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ======= Helper Methods =======

    private Employee extractEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();

        emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
        emp.setUsername(rs.getString("USERNAME"));
        emp.setFullName(rs.getString("FULLNAME"));
        emp.setGender(Employee.Gender.fromCode(rs.getString("GENDER").charAt(0)));
        emp.setCccd(rs.getString("CCCD"));
        emp.setDateOfBirth(rs.getDate("DATE_OF_BIRTH").toLocalDate());
        emp.setPhone(rs.getString("PHONE"));
        emp.setAddress(rs.getString("ADDRESS"));
        emp.setSalary(rs.getDouble("SALARY"));
        emp.setJoinDate(rs.getDate("JOIN_DATE").toLocalDate());
        emp.setImagePath(rs.getString("IMAGE_PATH"));

        int positionId = rs.getInt("POSITION_ID");
        String positionName = rs.getString("POSITION_NAME");  // lấy tên chức vụ
        emp.setPosition(new EmployeePosition(positionId, positionName));

        emp.setUser(new User(rs.getString("USERNAME"), null, null, null));

        return emp;
    }

    private void prepareEmployeeStatement(PreparedStatement stmt, Employee emp, boolean isUpdate) throws SQLException {
        if (!isUpdate) {
            stmt.setString(1, emp.getUsername());
            stmt.setString(2, emp.getFullName());
            stmt.setString(3, String.valueOf(emp.getGender()));
            stmt.setString(4, emp.getCccd());
            stmt.setDate(5, java.sql.Date.valueOf(emp.getDateOfBirth()));
            stmt.setString(6, emp.getPhone());
            stmt.setString(7, emp.getAddress());
            stmt.setInt(8, emp.getPosition().getPositionId());
            stmt.setDouble(9, emp.getSalary());
            stmt.setDate(10, java.sql.Date.valueOf(emp.getJoinDate()));
            stmt.setString(11, emp.getImagePath());
        } else {
            stmt.setString(1, emp.getFullName());
            stmt.setString(2, String.valueOf(emp.getGender()));
            stmt.setString(3, emp.getCccd());
            stmt.setDate(4, java.sql.Date.valueOf(emp.getDateOfBirth()));
            stmt.setString(5, emp.getPhone());
            stmt.setString(6, emp.getAddress());
            stmt.setInt(7, emp.getPosition().getPositionId());
            stmt.setDouble(8, emp.getSalary());
            stmt.setDate(9, java.sql.Date.valueOf(emp.getJoinDate()));
            stmt.setString(10, emp.getImagePath());
            stmt.setInt(11, emp.getEmployeeId());
        }
    }
}
