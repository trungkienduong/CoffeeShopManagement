package DAO;

import MODEL.Category;
import MODEL.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static ProductDAO instance;
    private CategoryDAO categoryDAO;
    
    private ProductDAO() {
        categoryDAO = CategoryDAO.getInstance();
    }
    
    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }
    
    // Thêm sản phẩm mới
    public boolean insert(Product product) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Product (ProductName, CategoryId, SellPrice) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, product.getProductName());
            pst.setInt(2, product.getCategoryId());
            pst.setDouble(3, product.getSellPrice());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật thông tin sản phẩm
    public boolean update(Product product) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE Product SET ProductName = ?, CategoryId = ?, SellPrice = ? WHERE ProductId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, product.getProductName());
            pst.setInt(2, product.getCategoryId());
            pst.setDouble(3, product.getSellPrice());
            pst.setInt(4, product.getProductId());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa sản phẩm
    public boolean delete(int productId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "DELETE FROM Product WHERE ProductId = ?";
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
    
    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Product";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryId"));
                product.setSellPrice(rs.getDouble("SellPrice"));
                
                // Lấy thông tin category
                Category category = categoryDAO.findById(product.getCategoryId());
                product.setCategory(category);
                
                productList.add(product);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    // Tìm sản phẩm theo ID
    public Product findById(int productId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Product WHERE ProductId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, productId);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryId"));
                product.setSellPrice(rs.getDouble("SellPrice"));
                
                // Lấy thông tin category
                Category category = categoryDAO.findById(product.getCategoryId());
                product.setCategory(category);
                
                DatabaseConnection.closeConnection(con);
                return product;
            }
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy danh sách sản phẩm theo danh mục
    public List<Product> findByCategory(int categoryId) {
        List<Product> productList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Product WHERE CategoryId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, categoryId);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryId"));
                product.setSellPrice(rs.getDouble("SellPrice"));
                
                // Lấy thông tin category
                Category category = categoryDAO.findById(product.getCategoryId());
                product.setCategory(category);
                
                productList.add(product);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    // Tìm sản phẩm theo tên (tìm kiếm gần đúng)
    public List<Product> searchByName(String keyword) {
        List<Product> productList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Product WHERE ProductName LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryId"));
                product.setSellPrice(rs.getDouble("SellPrice"));
                
                // Lấy thông tin category
                Category category = categoryDAO.findById(product.getCategoryId());
                product.setCategory(category);
                
                productList.add(product);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
