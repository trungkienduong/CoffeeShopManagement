package DAO;

import MODEL.ProductRecipe;
import MODEL.Product;
import MODEL.Inventory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRecipeDAO {
    private static ProductRecipeDAO instance;
    private ProductDAO productDAO;
    private InventoryDAO inventoryDAO;

    private ProductRecipeDAO() {
        productDAO = ProductDAO.getInstance();
        inventoryDAO = InventoryDAO.getInstance();
    }

    public static ProductRecipeDAO getInstance() {
        if (instance == null) {
            instance = new ProductRecipeDAO();
        }
        return instance;
    }

    //---------------------- INSERT ----------------------
    public boolean insert(ProductRecipe productRecipe) {
        String sql = "INSERT INTO [PRODUCT_RECIPE] (PRODUCT_ID, ITEM_ID, QUANTITY_USED) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productRecipe.getProduct().getProductId());
            pst.setInt(2, productRecipe.getItem().getItemId());
            pst.setBigDecimal(3, productRecipe.getQuantityUsed());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- UPDATE ----------------------
    public boolean update(ProductRecipe productRecipe) {
        String sql = "UPDATE [PRODUCT_RECIPE] SET QUANTITY_USED = ? WHERE PRODUCT_ID = ? AND ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setBigDecimal(1, productRecipe.getQuantityUsed());
            pst.setInt(2, productRecipe.getProduct().getProductId());
            pst.setInt(3, productRecipe.getItem().getItemId());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- DELETE ----------------------
    public boolean delete(int productId, int itemId) {
        String sql = "DELETE FROM [PRODUCT_RECIPE] WHERE PRODUCT_ID = ? AND ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);
            pst.setInt(2, itemId);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- DELETE BY PRODUCT ----------------------
    public boolean deleteByProduct(int productId) {
        String sql = "DELETE FROM [PRODUCT_RECIPE] WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- GET ALL ----------------------
    public List<ProductRecipe> getAll() {
        String sql = "SELECT * FROM [PRODUCT_RECIPE]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<ProductRecipe> list = new ArrayList<>();
            while (rs.next()) {
                ProductRecipe productRecipe = new ProductRecipe();

                int productId = rs.getInt("PRODUCT_ID");
                Product product = productDAO.findById(productId);
                productRecipe.setProduct(product);

                int itemId = rs.getInt("ITEM_ID");
                Inventory item = inventoryDAO.findById(itemId);
                productRecipe.setItem(item);

                productRecipe.setQuantityUsed(rs.getBigDecimal("QUANTITY_USED"));
                list.add(productRecipe);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //---------------------- GET BY PRODUCT ----------------------
    public List<ProductRecipe> findByProduct(int productId) {
        String sql = "SELECT * FROM [PRODUCT_RECIPE] WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            try (ResultSet rs = pst.executeQuery()) {
                List<ProductRecipe> list = new ArrayList<>();
                while (rs.next()) {
                    ProductRecipe productRecipe = new ProductRecipe();

                    productRecipe.setProduct(productDAO.findById(productId));

                    int itemId = rs.getInt("ITEM_ID");
                    Inventory item = inventoryDAO.findById(itemId);
                    productRecipe.setItem(item);

                    productRecipe.setQuantityUsed(rs.getBigDecimal("QUANTITY_USED"));
                    list.add(productRecipe);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //---------------------- GET BY ITEM ----------------------
    public List<ProductRecipe> findByItem(int itemId) {
        String sql = "SELECT * FROM [PRODUCT_RECIPE] WHERE ITEM_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, itemId);

            try (ResultSet rs = pst.executeQuery()) {
                List<ProductRecipe> list = new ArrayList<>();
                while (rs.next()) {
                    ProductRecipe productRecipe = new ProductRecipe();

                    int productId = rs.getInt("PRODUCT_ID");
                    Product product = productDAO.findById(productId);
                    productRecipe.setProduct(product);

                    productRecipe.setItem(inventoryDAO.findById(itemId));

                    productRecipe.setQuantityUsed(rs.getBigDecimal("QUANTITY_USED"));
                    list.add(productRecipe);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //---------------------- CHECK ----------------------
    public boolean checkIngredient(int productId) {       // check if product has any ingredients
        String sql = "SELECT * FROM [PRODUCT_RECIPE] WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
