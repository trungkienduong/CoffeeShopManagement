package DAO;

import MODEL.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class InventoryDAO {

    private Connection connection;

    // Constructor để khởi tạo kết nối tới cơ sở dữ liệu
    public InventoryDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức thêm một sản phẩm mới vào kho
    public boolean addInventory(Inventory inventory) {
        String query = "INSERT INTO INVENTORY (ITEM_NAME, CATEGORY_ID, QUANTITY, UNIT_ID, COST_PRICE, SELL_PRICE) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, inventory.getItemName());
            ps.setInt(2, inventory.getCategoryID());
            ps.setInt(3, inventory.getQuantity());
            ps.setInt(4, inventory.getUnitID());
            ps.setBigDecimal(5, inventory.getCostPrice());
            ps.setBigDecimal(6, inventory.getSellPrice());

            int result = ps.executeUpdate();
            return result > 0;  // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }

    // Phương thức sửa thông tin một sản phẩm trong kho
    public boolean updateInventory(Inventory inventory) {
        String query = "UPDATE INVENTORY SET ITEM_NAME = ?, CATEGORY_ID = ?, QUANTITY = ?, UNIT_ID = ?, COST_PRICE = ?, SELL_PRICE = ? WHERE ITEM_ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, inventory.getItemName());
            ps.setInt(2, inventory.getCategoryID());
            ps.setInt(3, inventory.getQuantity());
            ps.setInt(4, inventory.getUnitID());
            ps.setBigDecimal(5, inventory.getCostPrice());
            ps.setBigDecimal(6, inventory.getSellPrice());
            ps.setInt(7, inventory.getItemID());

            int result = ps.executeUpdate();
            return result > 0;  // Trả về true nếu sửa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }

    // Phương thức xóa một sản phẩm khỏi kho
    public boolean deleteInventory(int itemID) {
        String query = "DELETE FROM INVENTORY WHERE ITEM_ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, itemID);

            int result = ps.executeUpdate();
            return result > 0;  // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }

    // Phương thức lấy tất cả các sản phẩm trong kho
    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM INVENTORY";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Inventory inventory = new Inventory(
                        rs.getInt("ITEM_ID"),
                        rs.getString("ITEM_NAME"),
                        rs.getInt("CATEGORY_ID"),
                        rs.getInt("QUANTITY"),
                        rs.getInt("UNIT_ID"),
                        rs.getBigDecimal("COST_PRICE"),
                        rs.getBigDecimal("SELL_PRICE")
                );
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventoryList;
    }

    // Phương thức lấy thông tin một sản phẩm theo ID
    public Inventory getInventoryById(int itemID) {
        Inventory inventory = null;
        String query = "SELECT * FROM INVENTORY WHERE ITEM_ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, itemID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    inventory = new Inventory(
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    // Phương thức cập nhật số lượng tồn kho khi bán ra
    public boolean updateInventoryQuantity(int itemID, int quantity) {
        String query = "UPDATE INVENTORY SET QUANTITY = QUANTITY - ? WHERE ITEM_ID = ? AND QUANTITY >= ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, itemID);
            ps.setInt(3, quantity); // Đảm bảo không cho phép số lượng âm

            int result = ps.executeUpdate();
            return result > 0;  // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }
}
