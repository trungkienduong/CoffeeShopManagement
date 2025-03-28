package DAO;

import MODEL.EmployeePosition;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePositionDAO {

    private Connection connection;

    public EmployeePositionDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm một chức vụ mới vào bảng EmployeePosition
    public boolean addEmployeePosition(EmployeePosition employeePosition) {
        String sql = "INSERT INTO EMPLOYEE_POSITION (POSITION_NAME) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeePosition.getPositionName());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả các chức vụ từ bảng EmployeePosition
    public List<EmployeePosition> getAllEmployeePositions() {
        List<EmployeePosition> positions = new ArrayList<>();
        String sql = "SELECT * FROM EMPLOYEE_POSITION";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int positionID = resultSet.getInt("POSITION_ID");
                String positionName = resultSet.getString("POSITION_NAME");
                EmployeePosition position = new EmployeePosition(positionID, positionName);
                positions.add(position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    // Cập nhật tên chức vụ
    public boolean updateEmployeePosition(EmployeePosition employeePosition) {
        String sql = "UPDATE EMPLOYEE_POSITION SET POSITION_NAME = ? WHERE POSITION_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeePosition.getPositionName());
            statement.setInt(2, employeePosition.getPositionID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một chức vụ
    public boolean deleteEmployeePosition(int positionID) {
        String sql = "DELETE FROM EMPLOYEE_POSITION WHERE POSITION_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, positionID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin chức vụ theo ID
    public EmployeePosition getEmployeePositionByID(int positionID) {
        String sql = "SELECT * FROM EMPLOYEE_POSITION WHERE POSITION_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, positionID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String positionName = resultSet.getString("POSITION_NAME");
                    return new EmployeePosition(positionID, positionName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
