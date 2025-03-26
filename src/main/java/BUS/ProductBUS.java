package BUS;

import DAO.ProductDAO;
import MODEL.Product;
import java.util.List;

public class ProductBUS {
    private ProductDAO productDAO;

    public ProductBUS() {
        this.productDAO = new ProductDAO();
    }

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    // Xóa sản phẩm theo ID
    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }

    // Tìm kiếm sản phẩm theo tên hoặc loại
    public List<Product> searchProducts(String keyword) {
        return productDAO.searchProduct(keyword);
    }
}
