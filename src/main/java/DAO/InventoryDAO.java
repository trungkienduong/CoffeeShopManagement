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

    public List<Inventory> searchByNameAndUnit(String itemNameKeyword, Integer unitId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM INVENTORY WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (itemNameKeyword != null && !itemNameKeyword.trim().isEmpty()) {
            sql.append("AND ITEM_NAME LIKE ? ");
            params.add("%" + itemNameKeyword + "%");
        }
        if (unitId != null) {
            sql.append("AND UNIT_ID = ? ");
            params.add(unitId);
        }

        List<Inventory> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }

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

}
