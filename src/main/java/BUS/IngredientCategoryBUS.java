package BUS;

import DAO.IngredientCategoryDAO;
import MODEL.IngredientCategory;

import java.util.List;

public class IngredientCategoryBUS {
    private static IngredientCategoryBUS instance;
    private final IngredientCategoryDAO dao;

    private IngredientCategoryBUS() {
        dao = IngredientCategoryDAO.getInstance();
    }

    public static IngredientCategoryBUS getInstance() {
        if (instance == null) {
            instance = new IngredientCategoryBUS();
        }
        return instance;
    }

    public boolean addCategory(IngredientCategory category) {
        if (!isValid(category)) {
            return false;
        }
        return dao.insert(category);
    }

    public boolean updateCategory(IngredientCategory category) {
        if (!isValid(category) || category.getIngredientCategoryId() <= 0) {
            return false;
        }
        return dao.update(category);
    }

    public boolean deleteCategory(int id) {
        if (id <= 0) {
            return false;
        }
        return dao.delete(id);
    }

    public List<IngredientCategory> getAllCategories() {
        return dao.selectAll();
    }

    public IngredientCategory getCategoryById(int id) {
        if (id <= 0) return null;
        return dao.findById(id);
    }

    private boolean isValid(IngredientCategory category) {
        if (category == null) return false;
        String name = category.getIngredientCategoryName();
        if (name == null || name.trim().isEmpty()) return false;

        return true;
    }
}
