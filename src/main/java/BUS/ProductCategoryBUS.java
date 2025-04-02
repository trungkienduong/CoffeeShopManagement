package BUS;

import DAO.ProductCategoryDAO;
import MODEL.ProductCategory;
import java.sql.Connection;
import java.util.List;

public class ProductCategoryBUS {
    private ProductCategoryDAO productCategoryDAO;

    public ProductCategoryBUS(Connection connection) {
        this.productCategoryDAO = new ProductCategoryDAO(connection);
    }

    // Lấy tất cả danh mục sản phẩm
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryDAO.getAllProductCategories();
    }

    // Lấy thông tin danh mục sản phẩm theo ID
    public ProductCategory getProductCategoryByID(int categoryID) {
        return productCategoryDAO.getProductCategoryByID(categoryID);
    }

    // Thêm danh mục sản phẩm mới
    public boolean addProductCategory(ProductCategory productCategory) {
        if (productCategory == null || productCategory.getCategoryName().isEmpty()) {
            System.out.println("Thông tin danh mục không hợp lệ.");
            return false;
        }
        return productCategoryDAO.addProductCategory(productCategory);
    }

    // Cập nhật thông tin danh mục sản phẩm
    public boolean updateProductCategory(ProductCategory productCategory) {
        if (productCategory == null || productCategory.getCategoryID() <= 0 || productCategory.getCategoryName().isEmpty()) {
            System.out.println("Dữ liệu danh mục không hợp lệ.");
            return false;
        }
        return productCategoryDAO.updateProductCategory(productCategory);
    }

    // Xóa danh mục sản phẩm theo ID
    public boolean deleteProductCategory(int categoryID) {
        if (categoryID <= 0) {
            System.out.println("ID danh mục không hợp lệ.");
            return false;
        }
        return productCategoryDAO.deleteProductCategory(categoryID);
    }
}
