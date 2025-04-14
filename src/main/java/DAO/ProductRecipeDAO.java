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
    
    // Thêm công thức mới
    public boolean insert(ProductRecipe recipe) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO ProductRecipe (ProductId, ItemId, QuantityUsed) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, recipe.getProductId());
            pst.setInt(2, recipe.getItemId());
            pst.setDouble(3, recipe.getQuantityUsed());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật công thức
    public boolean update(ProductRecipe recipe) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE ProductRecipe SET QuantityUsed = ? WHERE ProductId = ? AND ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setDouble(1, recipe.getQuantityUsed());
            pst.setInt(2, recipe.getProductId());
            pst.setInt(3, recipe.getItemId());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa công thức
    public boolean delete(int productId, int itemId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "DELETE FROM ProductRecipe WHERE ProductId = ? AND ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, productId);
            pst.setInt(2, itemId);
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa tất cả công thức của một sản phẩm
    public boolean deleteByProduct(int productId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "DELETE FROM ProductRecipe WHERE ProductId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, productId);
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Lấy danh sách tất cả công thức
    public List<ProductRecipe> getAll() {
        List<ProductRecipe> recipeList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM ProductRecipe";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                ProductRecipe recipe = new ProductRecipe();
                recipe.setProductId(rs.getInt("ProductId"));
                recipe.setItemId(rs.getInt("ItemId"));
                recipe.setQuantityUsed(rs.getDouble("QuantityUsed"));
                
                // Lấy thông tin product và item
                Product product = productDAO.findById(recipe.getProductId());
                Inventory item = inventoryDAO.findById(recipe.getItemId());
                recipe.setProduct(product);
                recipe.setItem(item);
                
                recipeList.add(recipe);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
    
    // Lấy công thức của một sản phẩm
    public List<ProductRecipe> findByProduct(int productId) {
        List<ProductRecipe> recipeList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM ProductRecipe WHERE ProductId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, productId);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ProductRecipe recipe = new ProductRecipe();
                recipe.setProductId(productId);
                recipe.setItemId(rs.getInt("ItemId"));
                recipe.setQuantityUsed(rs.getDouble("QuantityUsed"));
                
                // Lấy thông tin product và item
                Product product = productDAO.findById(productId);
                Inventory item = inventoryDAO.findById(recipe.getItemId());
                recipe.setProduct(product);
                recipe.setItem(item);
                
                recipeList.add(recipe);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
    
    // Lấy danh sách sản phẩm sử dụng một nguyên liệu
    public List<ProductRecipe> findByItem(int itemId) {
        List<ProductRecipe> recipeList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM ProductRecipe WHERE ItemId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, itemId);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ProductRecipe recipe = new ProductRecipe();
                recipe.setProductId(rs.getInt("ProductId"));
                recipe.setItemId(itemId);
                recipe.setQuantityUsed(rs.getDouble("QuantityUsed"));
                
                // Lấy thông tin product và item
                Product product = productDAO.findById(recipe.getProductId());
                Inventory item = inventoryDAO.findById(itemId);
                recipe.setProduct(product);
                recipe.setItem(item);
                
                recipeList.add(recipe);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
    
    // Kiểm tra xem có đủ nguyên liệu để làm một sản phẩm không
    public boolean checkIngredients(int productId) {
        try {
            List<ProductRecipe> recipeList = findByProduct(productId);
            for (ProductRecipe recipe : recipeList) {
                if (!inventoryDAO.checkQuantity(recipe.getItemId(), recipe.getQuantityUsed())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
