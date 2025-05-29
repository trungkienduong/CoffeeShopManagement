package DAO;

import MODEL.ProductRecipe;
import MODEL.Category;
import MODEL.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRecipeDAO {
    private static ProductRecipeDAO instance;
    private CategoryDAO categoryDAO;
    private InventoryDAO inventoryDAO;

    private ProductRecipeDAO() {
        categoryDAO = CategoryDAO.getInstance();
        inventoryDAO = InventoryDAO.getInstance();
    }

    public static ProductRecipeDAO getInstance() {
        if (instance == null) {
            instance = new ProductRecipeDAO();
        }
        return instance;
    }

    public boolean insert(ProductRecipe recipe) {
        String sql = "INSERT INTO PRODUCT_RECIPE (CATEGORY_ID, ITEM_NAME, QUANTITY_USED) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, recipe.getCategory().getCategoryId());
            pst.setString(2, recipe.getItem().getItemName());
            pst.setBigDecimal(3, recipe.getQuantityUsed());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ProductRecipe recipe) {
        String sql = "UPDATE PRODUCT_RECIPE SET QUANTITY_USED = ? WHERE CATEGORY_ID = ? AND ITEM_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setBigDecimal(1, recipe.getQuantityUsed());
            pst.setInt(2, recipe.getCategory().getCategoryId());
            pst.setString(3, recipe.getItem().getItemName());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int categoryId, String itemName) {
        String sql = "DELETE FROM PRODUCT_RECIPE WHERE CATEGORY_ID = ? AND ITEM_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId);
            pst.setString(2, itemName);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteByCategory(int categoryId) {
        String sql = "DELETE FROM PRODUCT_RECIPE WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ProductRecipe> getAll() {
        String sql = "SELECT * FROM PRODUCT_RECIPE";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<ProductRecipe> list = new ArrayList<>();
            while (rs.next()) {
                ProductRecipe recipe = new ProductRecipe();

                int categoryId = rs.getInt("CATEGORY_ID");
                Category category = categoryDAO.findById(categoryId);
                recipe.setCategory(category);

                String itemName = rs.getString("ITEM_NAME");
                Inventory item = inventoryDAO.findByName(itemName);
                recipe.setItem(item);

                recipe.setQuantityUsed(rs.getBigDecimal("QUANTITY_USED"));
                list.add(recipe);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductRecipe> findByCategory(int categoryId) {
        String sql = "SELECT * FROM PRODUCT_RECIPE WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId);

            try (ResultSet rs = pst.executeQuery()) {
                List<ProductRecipe> list = new ArrayList<>();
                while (rs.next()) {
                    ProductRecipe recipe = new ProductRecipe();

                    recipe.setCategory(categoryDAO.findById(categoryId));

                    String itemName = rs.getString("ITEM_NAME");
                    Inventory item = inventoryDAO.findByName(itemName);
                    recipe.setItem(item);

                    recipe.setQuantityUsed(rs.getBigDecimal("QUANTITY_USED"));
                    list.add(recipe);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductRecipe> findByItem(String itemName) {
        String sql = "SELECT * FROM PRODUCT_RECIPE WHERE ITEM_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, itemName);

            try (ResultSet rs = pst.executeQuery()) {
                List<ProductRecipe> list = new ArrayList<>();
                while (rs.next()) {
                    ProductRecipe recipe = new ProductRecipe();

                    int categoryId = rs.getInt("CATEGORY_ID");
                    Category category = categoryDAO.findById(categoryId);
                    recipe.setCategory(category);

                    recipe.setItem(inventoryDAO.findByName(itemName));
                    recipe.setQuantityUsed(rs.getBigDecimal("QUANTITY_USED"));

                    list.add(recipe);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkHasIngredients(int categoryId) {
        String sql = "SELECT 1 FROM PRODUCT_RECIPE WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
