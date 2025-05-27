package DAO;

import MODEL.Inventory;
import MODEL.IngredientCategory;
import MODEL.UnitCategory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private static InventoryDAO instance;
    private final IngredientCategoryDAO ingredientCategoryDAO;
    private final UnitCategoryDAO unitCategoryDAO;

    private InventoryDAO() {
        ingredientCategoryDAO = IngredientCategoryDAO.getInstance();
        unitCategoryDAO = UnitCategoryDAO.getInstance();
    }

    public static InventoryDAO getInstance() {
        if (instance == null) {
            instance = new InventoryDAO();
        }
        return instance;
    }

    // INSERT
    public boolean insert(Inventory item) {
        String sql = "INSERT INTO INVENTORY (ITEM_NAME, INGREDIENT_CATEGORY_ID, UNIT_ID, QUANTITY, COST_PRICE) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, item.getItemName());
            pst.setInt(2, item.getIngredientCategory().getIngredientCategoryId());
            pst.setInt(3, item.getUnit().getUnitId());
            pst.setBigDecimal(4, item.getQuantity());
            pst.setBigDecimal(5, item.getCostPrice());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean update(Inventory item) {
        String sql = "UPDATE INVENTORY SET INGREDIENT_CATEGORY_ID = ?, UNIT_ID = ?, QUANTITY = ?, COST_PRICE = ? WHERE ITEM_NAME = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, item.getIngredientCategory().getIngredientCategoryId());
            pst.setInt(2, item.getUnit().getUnitId());
            pst.setBigDecimal(3, item.getQuantity());
            pst.setBigDecimal(4, item.getCostPrice());
            pst.setString(5, item.getItemName());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE QUANTITY
    public boolean updateQuantity(String itemName, BigDecimal newQuantity) {
        String sql = "UPDATE INVENTORY SET QUANTITY = ? WHERE ITEM_NAME = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setBigDecimal(1, newQuantity);
            pst.setString(2, itemName);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean delete(String itemName) {
        String sql = "DELETE FROM INVENTORY WHERE ITEM_NAME = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, itemName);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ALL
    public List<Inventory> getAll() {
        String sql = "SELECT * FROM INVENTORY";
        List<Inventory> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemName(rs.getString("ITEM_NAME"));
                item.setQuantity(rs.getBigDecimal("QUANTITY"));
                item.setCostPrice(rs.getBigDecimal("COST_PRICE"));
                item.setIngredientCategory(ingredientCategoryDAO.findById(rs.getInt("INGREDIENT_CATEGORY_ID")));
                item.setUnit(unitCategoryDAO.findById(rs.getInt("UNIT_ID")));
                list.add(item);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // FIND BY NAME
    public Inventory findByName(String itemName) {
        String sql = "SELECT * FROM INVENTORY WHERE ITEM_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, itemName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Inventory item = new Inventory();
                item.setItemName(rs.getString("ITEM_NAME"));
                item.setQuantity(rs.getBigDecimal("QUANTITY"));
                item.setCostPrice(rs.getBigDecimal("COST_PRICE"));
                item.setIngredientCategory(ingredientCategoryDAO.findById(rs.getInt("INGREDIENT_CATEGORY_ID")));
                item.setUnit(unitCategoryDAO.findById(rs.getInt("UNIT_ID")));
                return item;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // SEARCH BY NAME
    public List<Inventory> searchByName(String keyword) {
        String sql = "SELECT * FROM INVENTORY WHERE ITEM_NAME LIKE ?";
        List<Inventory> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Inventory item = new Inventory();
                item.setItemName(rs.getString("ITEM_NAME"));
                item.setQuantity(rs.getBigDecimal("QUANTITY"));
                item.setCostPrice(rs.getBigDecimal("COST_PRICE"));
                item.setIngredientCategory(ingredientCategoryDAO.findById(rs.getInt("INGREDIENT_CATEGORY_ID")));
                item.setUnit(unitCategoryDAO.findById(rs.getInt("UNIT_ID")));
                list.add(item);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // CHECK QUANTITY
    public boolean checkQuantity(String itemName, BigDecimal quantity) {
        String sql = "SELECT QUANTITY FROM INVENTORY WHERE ITEM_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, itemName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                BigDecimal current = rs.getBigDecimal("QUANTITY");
                return current.compareTo(quantity) >= 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
