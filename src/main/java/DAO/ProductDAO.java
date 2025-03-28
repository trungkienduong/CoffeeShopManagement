package DAO;

import MODEL.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    // Constructor để khởi tạo connection với cơ sở dữ liệu
    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức thêm sản phẩm mới
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO PRODUCT (NAME, CATEGORY_ID, COST_PRICE, SELL_PRICE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setBigDecimal(3, product.getCostPrice());
            ps.setBigDecimal(4, product.getSellPrice());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("PRODUCT_ID"),
                        rs.getString("NAME"),
                        rs.getInt("CATEGORY_ID"),
                        rs.getBigDecimal("COST_PRICE"),
                        rs.getBigDecimal("SELL_PRICE")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Phương thức lấy sản phẩm theo ID
    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                            rs.getInt("PRODUCT_ID"),
                            rs.getString("NAME"),
                            rs.getInt("CATEGORY_ID"),
                            rs.getBigDecimal("COST_PRICE"),
                            rs.getBigDecimal("SELL_PRICE")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Phương thức cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) {
        String sql = "UPDATE PRODUCT SET NAME = ?, CATEGORY_ID = ?, COST_PRICE = ?, SELL_PRICE = ? WHERE PRODUCT_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getCategoryID());
            ps.setBigDecimal(3, product.getCostPrice());
            ps.setBigDecimal(4, product.getSellPrice());
            ps.setInt(5, product.getProductID());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức xóa sản phẩm theo ID
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
