package BUS;

import DAO.ProductDAO;
import DAO.ProductRecipeDAO;
import MODEL.Category;
import MODEL.Product;

import java.util.List;

public class ProductBUS {
    private final ProductDAO productDAO;
    private final ProductRecipeDAO productRecipeDAO;

    // Singleton pattern (nếu bạn muốn dùng)
    private static ProductBUS instance;

    private ProductBUS() {
        productDAO = ProductDAO.getInstance();
        productRecipeDAO = ProductRecipeDAO.getInstance();
    }

    public static ProductBUS getInstance() {
        if (instance == null) {
            instance = new ProductBUS();
        }
        return instance;
    }

    /**
     * Kiểm tra tên sản phẩm đã tồn tại chưa (để tránh trùng lặp).
     */
    public boolean isProductNameExists(String productName) {
        List<Product> existingProducts = productDAO.searchByName(productName);
        return existingProducts.stream()
                .anyMatch(p -> p.getProductName().equalsIgnoreCase(productName));
    }

    /**
     * Thêm sản phẩm mới nếu tên chưa tồn tại.
     */
    public boolean addProduct(Product product) {
        if (isProductNameExists(product.getProductName())) {
            System.out.println("Sản phẩm đã tồn tại!");
            return false;
        }
        return productDAO.insert(product);
    }

    /**
     * Cập nhật thông tin sản phẩm.
     */
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    /**
     * Xóa sản phẩm theo ID, đồng thời xóa luôn các công thức liên quan.
     */
    public boolean deleteProduct(int productId) {
        // Xóa công thức sản phẩm trước
        productRecipeDAO.deleteByProduct(productId);

        // Xóa sản phẩm
        return productDAO.delete(productId);
    }

    /**
     * Lấy tất cả sản phẩm.
     */
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    /**
     * Lấy sản phẩm theo ID.
     */
    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    /**
     * Lấy danh sách sản phẩm theo danh mục.
     */
    public List<Product> getProductsByCategory(Category category) {
        return productDAO.findByCategory(category);
    }

    /**
     * Tìm kiếm sản phẩm theo từ khóa tên.
     */
    public List<Product> searchProductsByName(String keyword) {
        return productDAO.searchByName(keyword);
    }
}
