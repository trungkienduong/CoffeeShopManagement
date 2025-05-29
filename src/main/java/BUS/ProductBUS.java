package BUS;

import DAO.ProductDAO;
import DAO.ProductRecipeDAO;
import DAO.CategoryDAO;
import MODEL.Category;
import MODEL.Product;

import java.util.List;

public class ProductBUS {
    private final ProductDAO productDAO;
    private final ProductRecipeDAO productRecipeDAO;
    private final CategoryDAO categoryDAO;

    private static ProductBUS instance;

    private ProductBUS() {
        productDAO = ProductDAO.getInstance();
        productRecipeDAO = ProductRecipeDAO.getInstance();
        categoryDAO = CategoryDAO.getInstance();
    }

    public static ProductBUS getInstance() {
        if (instance == null) {
            instance = new ProductBUS();
        }
        return instance;
    }

    public boolean isProductNameExists(String productName) {
        if (productName == null || productName.trim().isEmpty()) return false;
        List<Product> existingProducts = productDAO.searchByName(productName);
        return existingProducts.stream()
                .anyMatch(p -> p.getProductName().equalsIgnoreCase(productName.trim()));
    }

    public boolean addProduct(Product product) {
        if (product == null || product.getProductName() == null) return false;
        if (isProductNameExists(product.getProductName())) {
            return false;
        }
        return productDAO.insert(product);
    }

    public boolean updateProduct(Product product) {
        if (product == null) return false;
        return productDAO.update(product);
    }

    public boolean deleteProduct(int productId) {
        Product product = productDAO.findById(productId);
        if (product == null) {
            return false;
        }

        int categoryId = product.getCategoryId();
        productRecipeDAO.deleteByCategory(categoryId);

        return productDAO.delete(productId);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    public List<Product> searchProductsByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return List.of();
        return productDAO.searchByName(keyword.trim());
    }

    public String getCategoryNameById(int categoryId) {
        Category category = categoryDAO.findById(categoryId);
        return category != null ? category.getCategoryName() : "Unknown";
    }
}
