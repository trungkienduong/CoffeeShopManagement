package BUS;

import DAO.ProductRecipeDAO;
import MODEL.ProductRecipe;
import MODEL.Category;
import MODEL.Inventory;

import java.math.BigDecimal;
import java.util.List;

public class ProductRecipeBUS {
    private static ProductRecipeBUS instance;
    private final ProductRecipeDAO productRecipeDAO;

    private ProductRecipeBUS() {
        productRecipeDAO = ProductRecipeDAO.getInstance();
    }

    public static ProductRecipeBUS getInstance() {
        if (instance == null) {
            instance = new ProductRecipeBUS();
        }
        return instance;
    }

    // Thêm công thức mới
    public boolean addProductRecipe(ProductRecipe recipe) {
        if (recipe == null
                || recipe.getCategory() == null || recipe.getCategory().getCategoryId() <= 0
                || recipe.getItem() == null || recipe.getItem().getItemName() == null || recipe.getItem().getItemName().isEmpty()
                || recipe.getQuantityUsed() == null || recipe.getQuantityUsed().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Thông tin công thức không hợp lệ!");
            return false;
        }
        return productRecipeDAO.insert(recipe);
    }

    // Cập nhật số lượng nguyên liệu sử dụng cho loại sản phẩm
    public boolean updateProductRecipe(ProductRecipe recipe) {
        if (recipe == null
                || recipe.getCategory() == null || recipe.getCategory().getCategoryId() <= 0
                || recipe.getItem() == null || recipe.getItem().getItemName() == null || recipe.getItem().getItemName().isEmpty()
                || recipe.getQuantityUsed() == null || recipe.getQuantityUsed().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Thông tin công thức không hợp lệ!");
            return false;
        }
        return productRecipeDAO.update(recipe);
    }

    // Xóa nguyên liệu ra khỏi công thức của loại sản phẩm
    public boolean deleteProductRecipe(int categoryId, String itemName) {
        if (categoryId <= 0 || itemName == null || itemName.isEmpty()) return false;
        return productRecipeDAO.delete(categoryId, itemName);
    }

    // Xóa toàn bộ công thức của một loại sản phẩm
    public boolean deleteByCategory(int categoryId) {
        if (categoryId <= 0) return false;
        return productRecipeDAO.deleteByCategory(categoryId);
    }

    // Lấy toàn bộ công thức
    public List<ProductRecipe> getAllRecipes() {
        return productRecipeDAO.getAll();
    }

    // Lấy danh sách nguyên liệu cho một loại sản phẩm
    public List<ProductRecipe> getRecipesByCategory(int categoryId) {
        if (categoryId <= 0) return null;
        return productRecipeDAO.findByCategory(categoryId);
    }

    // Lấy danh sách loại sản phẩm có dùng nguyên liệu này
    public List<ProductRecipe> getRecipesByItem(String itemName) {
        if (itemName == null || itemName.isEmpty()) return null;
        return productRecipeDAO.findByItem(itemName);
    }

    // Kiểm tra loại sản phẩm có công thức không
    public boolean hasIngredients(int categoryId) {
        if (categoryId <= 0) return false;
        return productRecipeDAO.checkHasIngredients(categoryId);
    }
}
