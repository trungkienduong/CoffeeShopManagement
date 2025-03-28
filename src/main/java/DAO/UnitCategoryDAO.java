package DAO;

import MODEL.UnitCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnitCategoryDAO {

    private Connection connection;

    public UnitCategoryDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm một đơn vị tính mới vào bảng UNIT_CATEGORY
    public boolean addUnit(UnitCategory unit) {
        String sql = "INSERT INTO UNIT_CATEGORY (UNIT_NAME, DESCRIPTION) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, unit.getUnitName());
            statement.setString(2, unit.getDescription());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả các đơn vị tính trong bảng UNIT_CATEGORY
    public List<UnitCategory> getAllUnits() {
        List<UnitCategory> units = new ArrayList<>();
        String sql = "SELECT * FROM UNIT_CATEGORY";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int unitID = resultSet.getInt("UNIT_ID");
                String unitName = resultSet.getString("UNIT_NAME");
                String description = resultSet.getString("DESCRIPTION");
                UnitCategory unit = new UnitCategory(unitID, unitName, description);
                units.add(unit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }

    // Cập nhật thông tin đơn vị tính
    public boolean updateUnit(UnitCategory unit) {
        String sql = "UPDATE UNIT_CATEGORY SET UNIT_NAME = ?, DESCRIPTION = ? WHERE UNIT_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, unit.getUnitName());
            statement.setString(2, unit.getDescription());
            statement.setInt(3, unit.getUnitID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa đơn vị tính theo UNIT_ID
    public boolean deleteUnit(int unitID) {
        String sql = "DELETE FROM UNIT_CATEGORY WHERE UNIT_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, unitID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin đơn vị tính theo UNIT_ID
    public UnitCategory getUnitByID(int unitID) {
        String sql = "SELECT * FROM UNIT_CATEGORY WHERE UNIT_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, unitID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String unitName = resultSet.getString("UNIT_NAME");
                    String description = resultSet.getString("DESCRIPTION");
                    return new UnitCategory(unitID, unitName, description);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
