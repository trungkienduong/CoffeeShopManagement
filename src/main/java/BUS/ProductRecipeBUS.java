package BUS;

import DAO.ProductRecipeDAO;
import MODEL.ProductRecipe;

import java.util.List;

public class ProductRecipeBUS {
    private final ProductRecipeDAO productRecipeDAO;

    public ProductRecipeBUS() {
        productRecipeDAO = ProductRecipeDAO.getInstance();
    }

    // Thêm công thức mới
    public boolean addProductRecipe(ProductRecipe recipe) {
        if (recipe == null || recipe.getProductId() <= 0 || recipe.getItemId() <= 0 || recipe.getQuantityUsed() <= 0) {
            System.out.println("Thông tin công thức không hợp lệ!");
            return false;
        }
        return productRecipeDAO.insert(recipe);
    }

    // Cập nhật số lượng nguyên liệu sử dụng cho sản phẩm
    public boolean updateProductRecipe(ProductRecipe recipe) {
        if (recipe == null || recipe.getProductId() <= 0 || recipe.getItemId() <= 0 || recipe.getQuantityUsed() <= 0) {
            System.out.println("Thông tin công thức không hợp lệ!");
            return false;
        }
        return productRecipeDAO.update(recipe);
    }

    // Xóa nguyên liệu ra khỏi công thức của sản phẩm
    public boolean deleteProductRecipe(int productId, int itemId) {
        if (productId <= 0 || itemId <= 0) return false;
        return productRecipeDAO.delete(productId, itemId);
    }

    // Xóa toàn bộ công thức của một sản phẩm
    public boolean deleteByProduct(int productId) {
        if (productId <= 0) return false;
        return productRecipeDAO.deleteByProduct(productId);
    }

    // Lấy toàn bộ công thức sản phẩm
    public List<ProductRecipe> getAllRecipes() {
        return productRecipeDAO.getAll();
    }

    // Lấy danh sách nguyên liệu dùng cho một sản phẩm
    public List<ProductRecipe> getRecipesByProduct(int productId) {
        if (productId <= 0) return null;
        return productRecipeDAO.findByProduct(productId);
    }

    // Lấy danh sách sản phẩm có dùng nguyên liệu này
    public List<ProductRecipe> getRecipesByItem(int itemId) {
        if (itemId <= 0) return null;
        return productRecipeDAO.findByItem(itemId);
    }

    // Kiểm tra sản phẩm có công thức không
    public boolean hasIngredients(int productId) {
        if (productId <= 0) return false;
        return productRecipeDAO.checkIngredient(productId);
    }
}
