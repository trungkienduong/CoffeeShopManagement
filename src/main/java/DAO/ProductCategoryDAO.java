package DAO;

import MODEL.ProductCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDAO {

    // Lấy tất cả loại sản phẩm
    public List<ProductCategory> getAllCategories() {
        List<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT_CATEGORY";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductCategory pc = new ProductCategory(
                        rs.getInt("CATEGORY_ID"),
                        rs.getString("CATEGORY_NAME"),
                        rs.getString("DESCRIPTION")
                );
                list.add(pc);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách loại sản phẩm: " + e.getMessage());
        }

        return list;
    }

    // Thêm loại sản phẩm mới
    public boolean addCategory(ProductCategory category) {
        String sql = "INSERT INTO PRODUCT_CATEGORY (CATEGORY_NAME, DESCRIPTION) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getCategory_Name());
            stmt.setString(2, category.getDescription());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi thêm loại sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Sửa loại sản phẩm
    public boolean updateCategory(ProductCategory category) {
        String sql = "UPDATE PRODUCT_CATEGORY SET CATEGORY_NAME = ?, DESCRIPTION = ? WHERE CATEGORY_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getCategory_Name());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getCategory_ID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật loại sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Xóa loại sản phẩm
    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM PRODUCT_CATEGORY WHERE CATEGORY_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi xóa loại sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm loại sản phẩm theo tên (hoặc mã)
    public List<ProductCategory> searchCategories(String keyword) {
        List<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT_CATEGORY WHERE CATEGORY_NAME LIKE ? OR CAST(CATEGORY_ID AS NVARCHAR) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductCategory pc = new ProductCategory(
                        rs.getInt("CATEGORY_ID"),
                        rs.getString("CATEGORY_NAME"),
                        rs.getString("DESCRIPTION")
                );
                list.add(pc);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm loại sản phẩm: " + e.getMessage());
        }

        return list;
    }
}
