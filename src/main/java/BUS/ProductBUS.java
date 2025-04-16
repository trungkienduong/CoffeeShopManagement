package BUS;

import DAO.ProductDAO;
import DAO.ProductRecipeDAO;  // Thêm DAO cho ProductRecipe
import MODEL.Product;
import MODEL.Category;
import java.util.List;

public class ProductBUS {
    private ProductDAO productDAO;
    private ProductRecipeDAO productRecipeDAO; // Khai báo thêm DAO cho ProductRecipe

    public ProductBUS() {
        productDAO = ProductDAO.getInstance();
        productRecipeDAO = ProductRecipeDAO.getInstance(); // Khởi tạo ProductRecipeDAO
    }

    // Kiểm tra xem tên sản phẩm có bị trùng hay không
    public boolean isProductNameExists(String productName) {
        List<Product> existingProducts = productDAO.searchByName(productName);
        return !existingProducts.isEmpty();
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) {
        if (isProductNameExists(product.getProductName())) {
            System.out.println("Sản phẩm đã tồn tại!");
            return false; // Nếu sản phẩm đã tồn tại thì không thêm
        }
        return productDAO.insert(product);
    }

    // Cập nhật sản phẩm
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    // Xóa sản phẩm và các công thức liên quan
    public boolean deleteProduct(int productId) {
        // Xóa các công thức sản phẩm trước khi xóa sản phẩm
        productRecipeDAO.deleteByProduct(productId);

        // Xóa sản phẩm
        return productDAO.delete(productId);
    }

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    // Lấy danh sách sản phẩm theo danh mục
    public List<Product> getProductsByCategory(Category category) {
        return productDAO.findByCategory(category);
    }

    // Tìm kiếm sản phẩm theo tên
    public List<Product> searchProductsByName(String keyword) {
        return productDAO.searchByName(keyword);
    }
}
