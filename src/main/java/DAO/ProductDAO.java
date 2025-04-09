package DAO;

import MODEL.Product;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = extractProduct(rs);
                list.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách sản phẩm: " + e.getMessage());
        }

        return list;
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO PRODUCT (NAME, CATEGORY_ID, COST_PRICE, SELL_PRICE) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getCategory_ID());
            stmt.setBigDecimal(3, product.getCostPrice());
            stmt.setBigDecimal(4, product.getSellPrice());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Sửa sản phẩm
    public boolean updateProduct(Product product) {
        String sql = "UPDATE PRODUCT SET NAME = ?, CATEGORY_ID = ?, COST_PRICE = ?, SELL_PRICE = ? WHERE PRODUCT_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getCategory_ID());
            stmt.setBigDecimal(3, product.getCostPrice());
            stmt.setBigDecimal(4, product.getSellPrice());
            stmt.setInt(5, product.getProduct_ID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm sản phẩm theo từ khóa (tên, ID, hoặc mã loại)
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT WHERE NAME LIKE ? OR CAST(PRODUCT_ID AS NVARCHAR) LIKE ? OR CAST(CATEGORY_ID AS NVARCHAR) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            stmt.setString(3, kw);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = extractProduct(rs);
                list.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm sản phẩm: " + e.getMessage());
        }

        return list;
    }

    // Hàm hỗ trợ để tách Product từ ResultSet
    private Product extractProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("PRODUCT_ID"),
                rs.getString("NAME"),
                rs.getInt("CATEGORY_ID"),
                rs.getBigDecimal("COST_PRICE"),
                rs.getBigDecimal("SELL_PRICE")
        );
    }
}
