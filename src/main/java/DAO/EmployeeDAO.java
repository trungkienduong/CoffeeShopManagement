package DAO;

import MODEL.Employee;
import MODEL.EmployeePosition;
import MODEL.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class EmployeeDAO {
    private static EmployeeDAO instance;
    private UserDAO userDAO;
    private EmployeePositionDAO positionDAO;
    
    private EmployeeDAO() {
        userDAO = UserDAO.getInstance();
        positionDAO = EmployeePositionDAO.getInstance();
    }
    
    public static EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }
    
    // Thêm nhân viên mới
    public boolean insert(Employee employee) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Employee (Username, Fullname, Gender, CCCD, DateOfBirth, Phone, Address, PositionId, Salary, JoinDate) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, employee.getUsername());
            pst.setString(2, employee.getFullname());
            pst.setString(3, String.valueOf(employee.getGender()));
            pst.setString(4, employee.getCccd());
            pst.setDate(5, new java.sql.Date(employee.getDateOfBirth().getTime()));
            pst.setString(6, employee.getPhone());
            pst.setString(7, employee.getAddress());
            pst.setInt(8, employee.getPositionId());
            pst.setDouble(9, employee.getSalary());
            pst.setDate(10, new java.sql.Date(employee.getJoinDate().getTime()));
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật thông tin nhân viên
    public boolean update(Employee employee) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE Employee SET Fullname = ?, Gender = ?, CCCD = ?, DateOfBirth = ?, " +
                        "Phone = ?, Address = ?, PositionId = ?, Salary = ? WHERE Username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, employee.getFullname());
            pst.setString(2, String.valueOf(employee.getGender()));
            pst.setString(3, employee.getCccd());
            pst.setDate(4, new java.sql.Date(employee.getDateOfBirth().getTime()));
            pst.setString(5, employee.getPhone());
            pst.setString(6, employee.getAddress());
            pst.setInt(7, employee.getPositionId());
            pst.setDouble(8, employee.getSalary());
            pst.setString(9, employee.getUsername());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa nhân viên
    public boolean delete(String username) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "DELETE FROM Employee WHERE Username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, username);
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Lấy danh sách tất cả nhân viên
    public List<Employee> getAll() {
        List<Employee> employeeList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Employee";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeId"));
                employee.setUsername(rs.getString("Username"));
                employee.setFullname(rs.getString("Fullname"));
                employee.setGender(rs.getString("Gender").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth"));
                employee.setPhone(rs.getString("Phone"));
                employee.setAddress(rs.getString("Address"));
                employee.setPositionId(rs.getInt("PositionId"));
                employee.setSalary(rs.getDouble("Salary"));
                employee.setJoinDate(rs.getDate("JoinDate"));
                
                // Lấy thông tin user và position
                User user = userDAO.findByUsername(employee.getUsername());
                EmployeePosition position = positionDAO.findById(employee.getPositionId());
                employee.setUser(user);
                employee.setPosition(position);
                
                employeeList.add(employee);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
    
    // Tìm nhân viên theo username
    public Employee findByUsername(String username) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Employee WHERE Username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeId"));
                employee.setUsername(rs.getString("Username"));
                employee.setFullname(rs.getString("Fullname"));
                employee.setGender(rs.getString("Gender").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth"));
                employee.setPhone(rs.getString("Phone"));
                employee.setAddress(rs.getString("Address"));
                employee.setPositionId(rs.getInt("PositionId"));
                employee.setSalary(rs.getDouble("Salary"));
                employee.setJoinDate(rs.getDate("JoinDate"));
                
                // Lấy thông tin user và position
                User user = userDAO.findByUsername(username);
                EmployeePosition position = positionDAO.findById(employee.getPositionId());
                employee.setUser(user);
                employee.setPosition(position);
                
                DatabaseConnection.closeConnection(con);
                return employee;
            }
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Tìm nhân viên theo chức vụ
    public List<Employee> findByPosition(int positionId) {
        List<Employee> employeeList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Employee WHERE PositionId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, positionId);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeId"));
                employee.setUsername(rs.getString("Username"));
                employee.setFullname(rs.getString("Fullname"));
                employee.setGender(rs.getString("Gender").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth"));
                employee.setPhone(rs.getString("Phone"));
                employee.setAddress(rs.getString("Address"));
                employee.setPositionId(rs.getInt("PositionId"));
                employee.setSalary(rs.getDouble("Salary"));
                employee.setJoinDate(rs.getDate("JoinDate"));
                
                // Lấy thông tin user và position
                User user = userDAO.findByUsername(employee.getUsername());
                EmployeePosition position = positionDAO.findById(positionId);
                employee.setUser(user);
                employee.setPosition(position);
                
                employeeList.add(employee);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
    
    // Tìm kiếm nhân viên theo tên (tìm kiếm gần đúng)
    public List<Employee> searchByName(String keyword) {
        List<Employee> employeeList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Employee WHERE Fullname LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeId"));
                employee.setUsername(rs.getString("Username"));
                employee.setFullname(rs.getString("Fullname"));
                employee.setGender(rs.getString("Gender").charAt(0));
                employee.setCccd(rs.getString("CCCD"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth"));
                employee.setPhone(rs.getString("Phone"));
                employee.setAddress(rs.getString("Address"));
                employee.setPositionId(rs.getInt("PositionId"));
                employee.setSalary(rs.getDouble("Salary"));
                employee.setJoinDate(rs.getDate("JoinDate"));
                
                // Lấy thông tin user và position
                User user = userDAO.findByUsername(employee.getUsername());
                EmployeePosition position = positionDAO.findById(employee.getPositionId());
                employee.setUser(user);
                employee.setPosition(position);
                
                employeeList.add(employee);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
