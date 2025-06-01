package BUS;

import DAO.ProductRecipeDAO;
import MODEL.ProductRecipe;

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

    public boolean addProductRecipe(ProductRecipe recipe) {
        if (!isValidRecipe(recipe)) return false;
        return productRecipeDAO.insert(recipe);
    }

    public boolean updateProductRecipe(ProductRecipe recipe) {
        if (!isValidRecipe(recipe)) return false;
        return productRecipeDAO.update(recipe);
    }

    public boolean deleteProductRecipe(int categoryId, String itemName) {
        if (categoryId <= 0 || itemName == null || itemName.isEmpty()) return false;
        return productRecipeDAO.delete(categoryId, itemName);
    }

    public boolean deleteByCategory(int categoryId) {
        if (categoryId <= 0) return false;
        return productRecipeDAO.deleteByCategory(categoryId);
    }

    public List<ProductRecipe> getAllRecipes() {
        return productRecipeDAO.getAll();
    }

    public List<ProductRecipe> getRecipesByCategory(int categoryId) {
        if (categoryId <= 0) return null;
        return productRecipeDAO.findByCategory(categoryId);
    }

    public List<ProductRecipe> getRecipesByItem(String itemName) {
        if (itemName == null || itemName.isEmpty()) return null;
        return productRecipeDAO.findByItem(itemName);
    }

    public boolean hasIngredients(int categoryId) {
        if (categoryId <= 0) return false;
        return productRecipeDAO.checkHasIngredients(categoryId);
    }

    private boolean isValidRecipe(ProductRecipe recipe) {
        if (recipe == null) return false;
        if (recipe.getCategory() == null || recipe.getCategory().getCategoryId() <= 0) return false;
        if (recipe.getItem() == null || recipe.getItem().getItemName() == null || recipe.getItem().getItemName().trim().isEmpty()) return false;
        if (recipe.getQuantityUsed() == null || recipe.getQuantityUsed().compareTo(BigDecimal.ZERO) <= 0) return false;
        return true;
    }


}
