package DAO;

import MODEL.Inventory;
import MODEL.Category;
import MODEL.UnitCategory;
import MODEL.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private static InventoryDAO instance;
    private CategoryDAO categoryDAO;
    private UnitCategoryDAO unitCategoryDAO;

    private InventoryDAO() {
        categoryDAO = CategoryDAO.getInstance();
        unitCategoryDAO = UnitCategoryDAO.getInstance();
    }

    public static InventoryDAO getInstance() {
        if (instance == null) {
            instance = new InventoryDAO();
        }
        return instance;
    }

    // ---------------------- INSERT ----------------------
    public boolean insert(Inventory item) {
        String sql = "INSERT INTO [INVENTORY] (ITEM_ID, ITEM_NAME, IMAGE_PATH, QUANTITY, CATEGORY_ID, UNIT_ID, COST_PRICE) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, item.getItemId());
            pst.setString(2, item.getItemName());
            pst.setString(3, item.getImagePath());
            pst.setDouble(4, item.getQuantity());
            pst.setInt(5, item.getCategory().getCategoryId());
            pst.setInt(6, item.getUnit().getUnitId());
            pst.setDouble(7, item.getCostPrice());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- UPDATE ----------------------
    public boolean update(Inventory item) {
        String sql = "UPDATE [INVENTORY] SET ITEM_NAME = ?, IMAGE_PATH = ?, QUANTITY = ?, CATEGORY_ID = ?, UNIT_ID = ?, COST_PRICE = ? WHERE ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, item.getItemName());
            pst.setString(2, item.getImagePath());
            pst.setDouble(3, item.getQuantity());
            pst.setInt(4, item.getCategory().getCategoryId());
            pst.setInt(5, item.getUnit().getUnitId());
            pst.setDouble(6, item.getCostPrice());
            pst.setInt(7, item.getItemId());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- UPDATE ----------------------
    public boolean updateQuantity(int itemId, double newQuantity) {
        String sql = "UPDATE [INVENTORY] SET QUANTITY = ? WHERE ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, newQuantity);
            pst.setInt(2, itemId);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- DELETE ----------------------
    public boolean delete(int itemId) {
        String sql = "DELETE FROM INVENTORY WHERE ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, itemId);
            int result = pst.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- GET ALL ----------------------
    public List<Inventory> getAll() {
        String sql = "SELECT * FROM [INVENTORY]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<Inventory> list = new ArrayList<>();
            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ITEM_ID"));
                item.setItemName(rs.getString("ITEM_NAME"));
                item.setImagePath(rs.getString("IMAGE_PATH"));
                item.setQuantity(rs.getDouble("QUANTITY"));
                item.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));

                int unitId = rs.getInt("UNIT_ID");
                UnitCategory unit = unitCategoryDAO.findById(unitId);
                item.setUnit(unit);

                item.setCostPrice(rs.getDouble("COST_PRICE"));
                list.add(item);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY ID ----------------------
    public Inventory findById(int itemId) {
        String sql = "SELECT * FROM [INVENTORY] WHERE ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, itemId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ITEM_ID"));
                item.setItemName(rs.getString("ITEM_NAME"));
                item.setImagePath(rs.getString("IMAGE_PATH"));
                item.setQuantity(rs.getDouble("QUANTITY"));
                item.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));

                int unitId = rs.getInt("UNIT_ID");
                UnitCategory unit = unitCategoryDAO.findById(unitId);
                item.setUnit(unit);
                item.setCostPrice(rs.getDouble("COST_PRICE"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------------- GET BY CATEGORY ----------------------
    public List<Inventory> findByCategory(int categoryId) {
        String sql = "SELECT * FROM [INVENTORY] WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId);

            ResultSet rs = pst.executeQuery();
            List<Inventory> list = new ArrayList<>();
            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ITEM_ID"));
                item.setItemName(rs.getString("ITEM_NAME"));
                item.setImagePath(rs.getString("IMAGE_PATH"));
                item.setQuantity(rs.getDouble("QUANTITY"));
                item.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));

                int unitId = rs.getInt("UNIT_ID");
                UnitCategory unit = unitCategoryDAO.findById(unitId);
                item.setUnit(unit);
                item.setCostPrice(rs.getDouble("COST_PRICE"));
                list.add(item);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- SEARCH BY NAME ----------------------
    public List<Inventory> searchByName(String keyword) {
        String sql = "SELECT * FROM [INVENTORY] WHERE ITEM_NAME LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();
            List<Inventory> list = new ArrayList<>();
            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemId(rs.getInt("ITEM_ID"));
                item.setItemName(rs.getString("ITEM_NAME"));
                item.setImagePath(rs.getString("IMAGE_PATH"));
                item.setQuantity(rs.getDouble("QUANTITY"));
                item.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));

                int unitId = rs.getInt("UNIT_ID");
                UnitCategory unit = unitCategoryDAO.findById(unitId);
                item.setUnit(unit);
                item.setCostPrice(rs.getDouble("COST_PRICE"));
                list.add(item);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- CHECK QUANTITY ----------------------
    public boolean checkQuantity(int itemId, double quantity) {
        String sql = "SELECT QUANTITY FROM [INVENTORY] WHERE ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, itemId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double currentQuantity = rs.getDouble("QUANTITY");
                return currentQuantity >= quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}