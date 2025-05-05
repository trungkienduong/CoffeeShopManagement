package DAO;

import MODEL.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static EmployeeDAO instance;
    private UserDAO userDAO;
    private EmployeePositionDAO employeePositionDAO;

    private EmployeeDAO() {
        userDAO = UserDAO.getInstance();
        employeePositionDAO = EmployeePositionDAO.getInstance();
    }

    public static EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }

    //---------------------- INSERT ----------------------
    public boolean insert(Employee employee) {
        String sql = "INSERT INTO [EMPLOYEE] (USERNAME, FULLNAME, GENDER, CCCD, DATE_OF_BIRTH, PHONE, ADDRESS, POSITION_ID, SALARY, IMAGE_PATH) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, employee.getUsername());
            pst.setString(2, employee.getFullname());
            pst.setString(3, String.valueOf(employee.getGender()));
            pst.setString(4, employee.getCccd());
            pst.setDate(5, new java.sql.Date(employee.getDateOfBirth().getTime()));
            pst.setString(6, employee.getPhone());
            pst.setString(7, employee.getAddress());
            pst.setInt(8, employee.getPositionId());
            pst.setDouble(9, employee.getSalary());
            pst.setString(10, employee.getImage());
            // thuc thi cau lenh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- UPDATE ----------------------
    public boolean update(Employee employee) {
        String sql = "UPDATE [EMPLOYEE] SET FULLNAME = ?, GENDER = ?, CCCD = ?, DATE_OF_BIRTH = ?, PHONE = ?, ADDRESS = ?, POSITION_ID = ?, SALARY = ?, IMAGE_PATH = ? WHERE EMPLOYEE_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, employee.getFullname());
            pst.setString(2, String.valueOf(employee.getGender()));
            pst.setString(3, employee.getCccd());
            pst.setDate(4, new java.sql.Date(employee.getDateOfBirth().getTime()));
            pst.setString(5, employee.getPhone());
            pst.setString(6, employee.getAddress());
            pst.setInt(7, employee.getPositionId());
            pst.setDouble(8, employee.getSalary());
            pst.setString(9, employee.getImage());
            pst.setInt(10, employee.getEmployeeId());

            // thuc thi cau lenh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- DELETE ----------------------
    public boolean delete(int employeeId) {
        String sql = "DELETE FROM [EMPLOYEE] WHERE EMPLOYEE_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, employeeId);

            // thuc thi cau lenh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- GET ALL ----------------------
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM [EMPLOYEE]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()){

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
                employee.setUsername(rs.getString("USERNAME"));
                employee.setFullname(rs.getString("FULLNAME"));
                employee.setGender(rs.getString("GENDER").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
                employee.setPhone(rs.getString("PHONE"));
                employee.setAddress(rs.getString("ADDRESS"));
                employee.setPositionId(rs.getInt("POSITION_ID"));
                employee.setSalary(rs.getDouble("SALARY"));
                employee.setImage(rs.getString("IMAGE_PATH"));
                employee.setJoinDate(rs.getDate("JOIN_DATE"));
                employee.setPosition(employeePositionDAO.findById(employee.getPositionId()));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    //---------------------- GET BY NAME ----------------------
    public Employee findByName(String username) {
        String sql = "SELECT * FROM [EMPLOYEE] WHERE USERNAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username); // gán giá trị cho ? thứ nhất
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
                employee.setUsername(rs.getString("USERNAME"));
                employee.setFullname(rs.getString("FULLNAME"));
                employee.setGender(rs.getString("GENDER").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
                employee.setPhone(rs.getString("PHONE"));
                employee.setAddress(rs.getString("ADDRESS"));
                employee.setPositionId(rs.getInt("POSITION_ID"));
                employee.setSalary(rs.getDouble("SALARY"));
                employee.setImage(rs.getString("IMAGE_PATH"));
                employee.setJoinDate(rs.getDate("JOIN_DATE"));
                employee.setPosition(employeePositionDAO.findById(employee.getPositionId()));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //---------------------- GET BY ID ----------------------
    public Employee findById(int employeeId) {
        String sql = "SELECT * FROM [EMPLOYEE] WHERE EMPLOYEE_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, employeeId); // gán giá trị cho ? thứ nhất
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
                employee.setUsername(rs.getString("USERNAME"));
                employee.setFullname(rs.getString("FULLNAME"));
                employee.setGender(rs.getString("GENDER").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
                employee.setPhone(rs.getString("PHONE"));
                employee.setAddress(rs.getString("ADDRESS"));
                employee.setPositionId(rs.getInt("POSITION_ID"));
                employee.setSalary(rs.getDouble("SALARY"));
                employee.setImage(rs.getString("IMAGE_PATH"));
                employee.setJoinDate(rs.getDate("JOIN_DATE"));
                employee.setPosition(employeePositionDAO.findById(employee.getPositionId()));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //---------------------- GET BY POSITION ----------------------
    public List<Employee> findByPosition(int positionId) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM [EMPLOYEE] WHERE POSITION_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, positionId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
                employee.setUsername(rs.getString("USERNAME"));
                employee.setFullname(rs.getString("FULLNAME"));
                employee.setGender(rs.getString("GENDER").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
                employee.setPhone(rs.getString("PHONE"));
                employee.setAddress(rs.getString("ADDRESS"));
                employee.setPositionId(rs.getInt("POSITION_ID"));
                employee.setSalary(rs.getDouble("SALARY"));
                employee.setImage(rs.getString("IMAGE_PATH"));
                employee.setJoinDate(rs.getDate("JOIN_DATE"));
                employee.setPosition(employeePositionDAO.findById(employee.getPositionId()));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    //---------------------- SEARCH BY NAME ----------------------
    public List<Employee> searchByName(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM [EMPLOYEE] WHERE FULLNAME LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
                employee.setUsername(rs.getString("USERNAME"));
                employee.setFullname(rs.getString("FULLNAME"));
                employee.setGender(rs.getString("GENDER").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
                employee.setPhone(rs.getString("PHONE"));
                employee.setAddress(rs.getString("ADDRESS"));
                employee.setPositionId(rs.getInt("POSITION_ID"));
                employee.setSalary(rs.getDouble("SALARY"));
                employee.setImage(rs.getString("IMAGE_PATH"));
                employee.setJoinDate(rs.getDate("JOIN_DATE"));
                employee.setPosition(employeePositionDAO.findById(employee.getPositionId()));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
