package DAO;

import MODEL.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM PRODUCT";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("PRODUCT_ID"),
                        rs.getString("NAME"),
                        rs.getString("CATEGORY"),
                        rs.getDouble("COST_PRICE"),
                        rs.getDouble("SELL_PRICE")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }
        return productList;
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) {
        String query = "INSERT INTO PRODUCT (NAME, CATEGORY, COST_PRICE, SELL_PRICE) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setDouble(3, product.getCostPrice());
            pstmt.setDouble(4, product.getSellPrice());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) {
        String query = "UPDATE PRODUCT SET NAME=?, CATEGORY=?, COST_PRICE=?, SELL_PRICE=? WHERE PRODUCT_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setDouble(3, product.getCostPrice());
            pstmt.setDouble(4, product.getSellPrice());
            pstmt.setInt(5, product.getProductID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Xóa sản phẩm theo ID
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM PRODUCT WHERE PRODUCT_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm sản phẩm theo tên hoặc loại
    public List<Product> searchProduct(String keyword) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM PRODUCT WHERE NAME LIKE ? OR CATEGORY LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("PRODUCT_ID"),
                        rs.getString("NAME"),
                        rs.getString("CATEGORY"),
                        rs.getDouble("COST_PRICE"),
                        rs.getDouble("SELL_PRICE")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
        }
        return productList;
    }
}
