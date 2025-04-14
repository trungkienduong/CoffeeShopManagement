package DAO;

import MODEL.Inventory;
import MODEL.Category;
import MODEL.UnitCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private static InventoryDAO instance;
    private CategoryDAO categoryDAO;
    private UnitCategoryDAO unitDAO;
    
    private InventoryDAO() {
        categoryDAO = CategoryDAO.getInstance();
        unitDAO = UnitCategoryDAO.getInstance();
    }
    
    public static InventoryDAO getInstance() {
        if (instance == null) {
            instance = new InventoryDAO();
        }
        return instance;
    }
    
    // Thêm nguyên liệu mới
    public boolean insert(Inventory item) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Inventory (ItemName, CategoryId, Quantity, UnitId, CostPrice) " +
                        "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, item.getItemName());
            pst.setInt(2, item.getCategoryId());
            pst.setDouble(3, item.getQuantity());
            pst.setInt(4, item.getUnitId());
            pst.setDouble(5, item.getCostPrice());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật thông tin nguyên liệu
    public boolean update(Inventory item) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE Inventory SET ItemName = ?, CategoryId = ?, Quantity = ?, " +
                        "UnitId = ?, CostPrice = ? WHERE ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, item.getItemName());
            pst.setInt(2, item.getCategoryId());
            pst.setDouble(3, item.getQuantity());
            pst.setInt(4, item.getUnitId());
            pst.setDouble(5, item.getCostPrice());
            pst.setInt(6, item.getItemId());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật số lượng nguyên liệu
    public boolean updateQuantity(int itemId, double newQuantity) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE Inventory SET Quantity = ? WHERE ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setDouble(1, newQuantity);
            pst.setInt(2, itemId);
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa nguyên liệu
    public boolean delete(int itemId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "DELETE FROM Inventory WHERE ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, itemId);
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Lấy danh sách tất cả nguyên liệu
    public List<Inventory> getAll() {
        List<Inventory> inventoryList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Inventory";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ItemId"));
                item.setItemName(rs.getString("ItemName"));
                item.setCategoryId(rs.getInt("CategoryId"));
                item.setQuantity(rs.getDouble("Quantity"));
                item.setUnitId(rs.getInt("UnitId"));
                item.setCostPrice(rs.getDouble("CostPrice"));
                
                // Lấy thông tin category và unit
                Category category = categoryDAO.findById(item.getCategoryId());
                UnitCategory unit = unitDAO.findById(item.getUnitId());
                item.setCategory(category);
                item.setUnit(unit);
                
                inventoryList.add(item);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }
    
    // Tìm nguyên liệu theo ID
    public Inventory findById(int itemId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Inventory WHERE ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, itemId);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ItemId"));
                item.setItemName(rs.getString("ItemName"));
                item.setCategoryId(rs.getInt("CategoryId"));
                item.setQuantity(rs.getDouble("Quantity"));
                item.setUnitId(rs.getInt("UnitId"));
                item.setCostPrice(rs.getDouble("CostPrice"));
                
                // Lấy thông tin category và unit
                Category category = categoryDAO.findById(item.getCategoryId());
                UnitCategory unit = unitDAO.findById(item.getUnitId());
                item.setCategory(category);
                item.setUnit(unit);
                
                DatabaseConnection.closeConnection(con);
                return item;
            }
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Tìm nguyên liệu theo danh mục
    public List<Inventory> findByCategory(int categoryId) {
        List<Inventory> inventoryList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Inventory WHERE CategoryId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, categoryId);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ItemId"));
                item.setItemName(rs.getString("ItemName"));
                item.setCategoryId(categoryId);
                item.setQuantity(rs.getDouble("Quantity"));
                item.setUnitId(rs.getInt("UnitId"));
                item.setCostPrice(rs.getDouble("CostPrice"));
                
                // Lấy thông tin category và unit
                Category category = categoryDAO.findById(categoryId);
                UnitCategory unit = unitDAO.findById(item.getUnitId());
                item.setCategory(category);
                item.setUnit(unit);
                
                inventoryList.add(item);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }
    
    // Tìm kiếm nguyên liệu theo tên (tìm kiếm gần đúng)
    public List<Inventory> searchByName(String keyword) {
        List<Inventory> inventoryList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Inventory WHERE ItemName LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ItemId"));
                item.setItemName(rs.getString("ItemName"));
                item.setCategoryId(rs.getInt("CategoryId"));
                item.setQuantity(rs.getDouble("Quantity"));
                item.setUnitId(rs.getInt("UnitId"));
                item.setCostPrice(rs.getDouble("CostPrice"));
                
                // Lấy thông tin category và unit
                Category category = categoryDAO.findById(item.getCategoryId());
                UnitCategory unit = unitDAO.findById(item.getUnitId());
                item.setCategory(category);
                item.setUnit(unit);
                
                inventoryList.add(item);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }
    
    // Kiểm tra số lượng tồn kho
    public boolean checkQuantity(int itemId, double requiredQuantity) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT Quantity FROM Inventory WHERE ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, itemId);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double currentQuantity = rs.getDouble("Quantity");
                DatabaseConnection.closeConnection(con);
                return currentQuantity >= requiredQuantity;
            }
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
