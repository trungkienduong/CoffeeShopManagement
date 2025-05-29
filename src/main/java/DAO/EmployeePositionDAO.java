package DAO;

import MODEL.EmployeePosition;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePositionDAO {
    private static EmployeePositionDAO instance;

    private EmployeePositionDAO() {}

    public static EmployeePositionDAO getInstance() {
        if (instance == null) {
            instance = new EmployeePositionDAO();
        }
        return instance;
    }

    public List<EmployeePosition> selectAll() {
        List<EmployeePosition> list = new ArrayList<>();
        String query = "SELECT * FROM EMPLOYEE_POSITION";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EmployeePosition position = new EmployeePosition(
                        rs.getInt("POSITION_ID"),
                        rs.getString("POSITION_NAME")
                );
                list.add(position);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(EmployeePosition position) {
        String query = "INSERT INTO EMPLOYEE_POSITION (POSITION_NAME) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, position.getPositionName());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(EmployeePosition position) {
        String query = "UPDATE EMPLOYEE_POSITION SET POSITION_NAME = ? WHERE POSITION_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, position.getPositionName());
            stmt.setInt(2, position.getPositionId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int positionId) {
        String query = "DELETE FROM EMPLOYEE_POSITION WHERE POSITION_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, positionId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public EmployeePosition findById(int positionId) {
        String query = "SELECT * FROM EMPLOYEE_POSITION WHERE POSITION_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, positionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new EmployeePosition(
                            rs.getInt("POSITION_ID"),
                            rs.getString("POSITION_NAME")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
