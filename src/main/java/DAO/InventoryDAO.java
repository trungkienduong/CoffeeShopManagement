package DAO;

import MODEL.Inventory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    // Lấy danh sách tất cả hàng tồn kho
    public List<Inventory> getAllInventoryItems() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM INVENTORY";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inventory item = extractInventory(rs);
                list.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách INVENTORY: " + e.getMessage());
        }

        return list;
    }

    // Thêm mặt hàng mới vào kho
    public boolean addInventoryItem(Inventory item) {
        String sql = "INSERT INTO INVENTORY (ITEM_NAME, CATEGORY_ID, QUANTITY, UNIT_ID, COST_PRICE, SELL_PRICE) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItem_Name());
            stmt.setInt(2, item.getCategory_ID());
            stmt.setInt(3, item.getQuantity());
            stmt.setInt(4, item.getUnit_ID());
            stmt.setBigDecimal(5, item.getCostPrice());
            stmt.setBigDecimal(6, item.getSellPrice());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm INVENTORY: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật mặt hàng trong kho
    public boolean updateInventoryItem(Inventory item) {
        String sql = "UPDATE INVENTORY SET ITEM_NAME = ?, CATEGORY_ID = ?, QUANTITY = ?, UNIT_ID = ?, " +
                "COST_PRICE = ?, SELL_PRICE = ? WHERE ITEM_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItem_Name());
            stmt.setInt(2, item.getCategory_ID());
            stmt.setInt(3, item.getQuantity());
            stmt.setInt(4, item.getUnit_ID());
            stmt.setBigDecimal(5, item.getCostPrice());
            stmt.setBigDecimal(6, item.getSellPrice());
            stmt.setInt(7, item.getItem_ID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật INVENTORY: " + e.getMessage());
            return false;
        }
    }

    // Xóa mặt hàng khỏi kho
    public boolean deleteInventoryItem(int itemId) {
        String sql = "DELETE FROM INVENTORY WHERE ITEM_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xoá INVENTORY: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm hàng tồn kho theo tên, mã hoặc số lượng
    public List<Inventory> searchInventoryItems(String keyword) {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM INVENTORY WHERE " +
                "ITEM_NAME LIKE ? OR CAST(ITEM_ID AS NVARCHAR) LIKE ? OR CAST(QUANTITY AS NVARCHAR) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            stmt.setString(3, kw);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inventory item = extractInventory(rs);
                list.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm INVENTORY: " + e.getMessage());
        }

        return list;
    }

    // Trích xuất đối tượng Inventory từ ResultSet
    private Inventory extractInventory(ResultSet rs) throws SQLException {
        return new Inventory(
                rs.getInt("ITEM_ID"),
                rs.getString("ITEM_NAME"),
                rs.getInt("CATEGORY_ID"),
                rs.getInt("QUANTITY"),
                rs.getInt("UNIT_ID"),
                rs.getBigDecimal("COST_PRICE"),
                rs.getBigDecimal("SELL_PRICE")
        );
    }
}
