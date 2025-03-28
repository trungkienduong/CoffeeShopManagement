package DAO;

import MODEL.InventoryCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryCategoryDAO {

    private Connection connection;

    public InventoryCategoryDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm một danh mục nguyên liệu mới
    public boolean addInventoryCategory(InventoryCategory inventoryCategory) {
        String sql = "INSERT INTO INVENTORY_CATEGORY (CATEGORY_NAME, DESCRIPTION) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, inventoryCategory.getCategoryName());
            statement.setString(2, inventoryCategory.getDescription());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả danh mục nguyên liệu
    public List<InventoryCategory> getAllInventoryCategories() {
        List<InventoryCategory> categories = new ArrayList<>();
        String sql = "SELECT * FROM INVENTORY_CATEGORY";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CATEGORY_ID");
                String categoryName = resultSet.getString("CATEGORY_NAME");
                String description = resultSet.getString("DESCRIPTION");
                InventoryCategory category = new InventoryCategory(categoryID, categoryName, description);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Cập nhật thông tin danh mục nguyên liệu
    public boolean updateInventoryCategory(InventoryCategory inventoryCategory) {
        String sql = "UPDATE INVENTORY_CATEGORY SET CATEGORY_NAME = ?, DESCRIPTION = ? WHERE CATEGORY_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, inventoryCategory.getCategoryName());
            statement.setString(2, inventoryCategory.getDescription());
            statement.setInt(3, inventoryCategory.getCategoryID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa danh mục nguyên liệu
    public boolean deleteInventoryCategory(int categoryID) {
        String sql = "DELETE FROM INVENTORY_CATEGORY WHERE CATEGORY_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin danh mục nguyên liệu theo ID
    public InventoryCategory getInventoryCategoryByID(int categoryID) {
        String sql = "SELECT * FROM INVENTORY_CATEGORY WHERE CATEGORY_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String categoryName = resultSet.getString("CATEGORY_NAME");
                    String description = resultSet.getString("DESCRIPTION");
                    return new InventoryCategory(categoryID, categoryName, description);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
