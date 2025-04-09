package DAO;

import MODEL.InventoryCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryCategoryDAO {

    // Lấy tất cả danh mục hàng trong kho
    public List<InventoryCategory> getAllInventoryCategories() {
        List<InventoryCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM INVENTORY_CATEGORY";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InventoryCategory category = extractInventoryCategory(rs);
                list.add(category);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách INVENTORY_CATEGORY: " + e.getMessage());
        }

        return list;
    }

    // Thêm danh mục mới
    public boolean addInventoryCategory(InventoryCategory category) {
        String sql = "INSERT INTO INVENTORY_CATEGORY (CATEGORY_NAME, DESCRIPTION) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getCategory_Name());
            stmt.setString(2, category.getDescription());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm INVENTORY_CATEGORY: " + e.getMessage());
            return false;
        }
    }

    // Sửa danh mục
    public boolean updateInventoryCategory(InventoryCategory category) {
        String sql = "UPDATE INVENTORY_CATEGORY SET CATEGORY_NAME = ?, DESCRIPTION = ? WHERE CATEGORY_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getCategory_Name());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getCategory_ID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật INVENTORY_CATEGORY: " + e.getMessage());
            return false;
        }
    }

    // Xóa danh mục
    public boolean deleteInventoryCategory(int categoryId) {
        String sql = "DELETE FROM INVENTORY_CATEGORY WHERE CATEGORY_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa INVENTORY_CATEGORY: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm danh mục theo tên hoặc ID
    public List<InventoryCategory> searchInventoryCategories(String keyword) {
        List<InventoryCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM INVENTORY_CATEGORY WHERE CATEGORY_NAME LIKE ? OR CAST(CATEGORY_ID AS NVARCHAR) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InventoryCategory category = extractInventoryCategory(rs);
                list.add(category);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm INVENTORY_CATEGORY: " + e.getMessage());
        }

        return list;
    }

    // Hàm tách dữ liệu từ ResultSet
    private InventoryCategory extractInventoryCategory(ResultSet rs) throws SQLException {
        return new InventoryCategory(
                rs.getInt("CATEGORY_ID"),
                rs.getString("CATEGORY_NAME"),
                rs.getString("DESCRIPTION")
        );
    }
}
