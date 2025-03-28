package DAO;

import MODEL.ProductCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDAO {

    private Connection connection;

    public ProductCategoryDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm một danh mục sản phẩm mới
    public boolean addProductCategory(ProductCategory productCategory) {
        String sql = "INSERT INTO PRODUCT_CATEGORY (CATEGORY_NAME, DESCRIPTION) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productCategory.getCategoryName());
            statement.setString(2, productCategory.getDescription());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả danh mục sản phẩm
    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> categories = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT_CATEGORY";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CATEGORY_ID");
                String categoryName = resultSet.getString("CATEGORY_NAME");
                String description = resultSet.getString("DESCRIPTION");
                ProductCategory category = new ProductCategory(categoryID, categoryName, description);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Cập nhật thông tin danh mục sản phẩm
    public boolean updateProductCategory(ProductCategory productCategory) {
        String sql = "UPDATE PRODUCT_CATEGORY SET CATEGORY_NAME = ?, DESCRIPTION = ? WHERE CATEGORY_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productCategory.getCategoryName());
            statement.setString(2, productCategory.getDescription());
            statement.setInt(3, productCategory.getCategoryID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa danh mục sản phẩm
    public boolean deleteProductCategory(int categoryID) {
        String sql = "DELETE FROM PRODUCT_CATEGORY WHERE CATEGORY_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin danh mục sản phẩm theo ID
    public ProductCategory getProductCategoryByID(int categoryID) {
        String sql = "SELECT * FROM PRODUCT_CATEGORY WHERE CATEGORY_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String categoryName = resultSet.getString("CATEGORY_NAME");
                    String description = resultSet.getString("DESCRIPTION");
                    return new ProductCategory(categoryID, categoryName, description);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
