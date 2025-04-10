package DAO;

import MODEL.EmployeePosition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePositionDAO {

    // Lấy tất cả chức vụ
    public List<EmployeePosition> getAllPositions() {
        List<EmployeePosition> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM EMPLOYEE_POSITION");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("POSITION_ID");
                String name = rs.getString("POSITION_NAME");
                list.add(new EmployeePosition(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách chức vụ: " + e.getMessage());
        }
        return list;
    }

    // Trả về ObservableList cho ComboBox
    public ObservableList<EmployeePosition> getObservablePositions() {
        ObservableList<EmployeePosition> list = FXCollections.observableArrayList();
        list.addAll(getAllPositions());
        return list;
    }

    // Tìm chức vụ theo ID
    public EmployeePosition findById(int id) {
        String sql = "SELECT * FROM EMPLOYEE_POSITION WHERE POSITION_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new EmployeePosition(rs.getInt("POSITION_ID"), rs.getString("POSITION_NAME"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm chức vụ theo ID: " + e.getMessage());
        }
        return null;
    }

    // Thêm chức vụ
    public boolean insertPosition(EmployeePosition position) {
        String sql = "INSERT INTO EMPLOYEE_POSITION (POSITION_NAME) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, position.getPosition_Name());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chức vụ: " + e.getMessage());
        }
        return false;
    }

    // Cập nhật chức vụ
    public boolean updatePosition(EmployeePosition position) {
        String sql = "UPDATE EMPLOYEE_POSITION SET POSITION_NAME = ? WHERE POSITION_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, position.getPosition_Name());
            stmt.setInt(2, position.getPosition_ID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chức vụ: " + e.getMessage());
        }
        return false;
    }

    // Xóa chức vụ
    public boolean deletePosition(int positionID) {
        String sql = "DELETE FROM EMPLOYEE_POSITION WHERE POSITION_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, positionID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chức vụ: " + e.getMessage());
        }
        return false;
    }
}
