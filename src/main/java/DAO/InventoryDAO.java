package DAO;

import MODEL.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    // Lấy danh sách tất cả nguyên liệu trong kho
    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM INVENTORY";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Inventory item = new Inventory(
                        rs.getInt("ITEM_ID"),
                        rs.getString("ITEM_NAME"),
                        rs.getString("CATEGORY"),
                        rs.getInt("QUANTITY"),
                        rs.getString("UNIT"),
                        rs.getDouble("SELL_PRICE")
                );
                inventoryList.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách kho: " + e.getMessage());
        }
        return inventoryList;
    }

    // Thêm nguyên liệu mới
    public boolean addInventory(Inventory item) {
        String query = "INSERT INTO INVENTORY (ITEM_NAME, CATEGORY, QUANTITY, UNIT, SELL_PRICE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getCategory());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setString(4, item.getUnit());
            pstmt.setDouble(5, item.getSellPrice());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nguyên liệu: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin nguyên liệu
    public boolean updateInventory(Inventory item) {
        String query = "UPDATE INVENTORY SET ITEM_NAME=?, CATEGORY=?, QUANTITY=?, UNIT=?, SELL_PRICE=? WHERE ITEM_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getCategory());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setString(4, item.getUnit());
            pstmt.setDouble(5, item.getSellPrice());
            pstmt.setInt(6, item.getItemID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nguyên liệu: " + e.getMessage());
            return false;
        }
    }

    // Xóa nguyên liệu theo ID
    public boolean deleteInventory(int itemId) {
        String query = "DELETE FROM INVENTORY WHERE ITEM_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, itemId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nguyên liệu: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm nguyên liệu theo tên hoặc loại
    public List<Inventory> searchInventory(String keyword) {
        List<Inventory> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM INVENTORY WHERE ITEM_NAME LIKE ? OR CATEGORY LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Inventory item = new Inventory(
                        rs.getInt("ITEM_ID"),
                        rs.getString("ITEM_NAME"),
                        rs.getString("CATEGORY"),
                        rs.getInt("QUANTITY"),
                        rs.getString("UNIT"),
                        rs.getDouble("SELL_PRICE")
                );
                inventoryList.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nguyên liệu: " + e.getMessage());
        }
        return inventoryList;
    }
}
