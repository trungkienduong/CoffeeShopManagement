package DAO;

import MODEL.EmployeePosition;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeePositionDAO {
    private static EmployeePositionDAO instance;

    private EmployeePositionDAO() {
    }

    public static EmployeePositionDAO getInstance() {
        if (instance == null) {
            instance = new EmployeePositionDAO();
        }
        return instance;
    }

    // ---------------------- INSERT ----------------------
    public boolean insert(EmployeePosition employeePosition) {
        String sql = "INSERT INTO [EMPLOYEE_POSITION] (POSITION_NAME) VALUES (?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, employeePosition.getPositionName()); // gán giá trị cho ? thứ nhất

            // thực thi câu lệnh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- UPDATE ----------------------
    public boolean update(EmployeePosition employeePosition) {
        String sql = "UPDATE [EMPLOYEE_POSITION] SET POSITION_NAME = ? WHERE POSITION_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, employeePosition.getPositionName()); // gán giá trị cho ? thứ hai
            pst.setInt(2, employeePosition.getPositionId()); // gán giá trị cho ? thứ hai

            // thực thi câu lệnh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- DELETE ----------------------
    public boolean delete(int positionId) {
        String sql = "DELETE FROM [EMPLOYEE_POSITION] WHERE POSITION_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, positionId);

            // thuc thi cau lenh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- GET ALL ----------------------

    public List<EmployeePosition> getAll() {
        String sql = "SELECT * FROM [EMPLOYEE_POSITION]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<EmployeePosition> list = new ArrayList<>();
            while (rs.next()) {
                EmployeePosition employeePosition = new EmployeePosition();
                employeePosition.setPositionId(rs.getInt("POSITION_ID"));
                employeePosition.setPositionName(rs.getString("POSITION_NAME"));
                list.add(employeePosition);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY NAME ----------------------
    public EmployeePosition findByName(String positionName) {
        String sql = "SELECT * FROM [EMPLOYEE_POSITION] WHERE POSITION_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, positionName);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                EmployeePosition employeePosition = new EmployeePosition();
                employeePosition.setPositionId(rs.getInt("POSITION_ID"));
                employeePosition.setPositionName(rs.getString("POSITION_NAME"));
                return employeePosition;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //---------------------- GET BY ID ----------------------
    public EmployeePosition findById(int positionId) {
        String sql = "SELECT * FROM [EMPLOYEE_POSITION] WHERE POSITION_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, positionId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                EmployeePosition employeePosition = new EmployeePosition();
                employeePosition.setPositionId(rs.getInt("POSITION_ID"));
                employeePosition.setPositionName(rs.getString("POSITION_NAME"));
                return employeePosition;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
