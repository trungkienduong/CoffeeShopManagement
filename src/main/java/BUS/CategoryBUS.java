package BUS;

import DAO.CategoryDAO;
import MODEL.Category;

import java.util.Collections;
import java.util.List;

public class CategoryBUS {
    private static CategoryBUS instance;
    private final CategoryDAO categoryDAO;

    private CategoryBUS() {
        categoryDAO = CategoryDAO.getInstance();
    }

    public static CategoryBUS getInstance() {
        if (instance == null) {
            instance = new CategoryBUS();
        }
        return instance;
    }

    public boolean insertCategory(Category category) {
        if (category == null || category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return false;
        }

        if (categoryDAO.findByName(category.getCategoryName().trim()) != null) {
            return false;
        }

        return categoryDAO.insert(category);
    }

    public boolean addCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return false;
        }

        if (categoryDAO.findByName(categoryName.trim()) != null) {
            return false;
        }

        Category category = new Category(categoryName.trim());
        return categoryDAO.insert(category);
    }

    public boolean updateCategory(int id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }

        Category existing = categoryDAO.findById(id);
        if (existing == null) {
            return false;
        }

        Category byName = categoryDAO.findByName(newName.trim());
        if (byName != null && byName.getCategoryId() != id) {
            return false;
        }

        existing.setCategoryName(newName.trim());
        return categoryDAO.update(existing);
    }

    public boolean deleteCategory(int id) {
        Category category = categoryDAO.findById(id);
        if (category == null) {
            return false;
        }

        return categoryDAO.delete(id);
    }

    public Category getCategoryById(int id) {
        return categoryDAO.findById(id);
    }

    public Category getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        return categoryDAO.findByName(name.trim());
    }

    public List<Category> getAllCategories() {
        List<Category> list = categoryDAO.getAll();
        return list != null ? list : Collections.emptyList();
    }

    public List<Category> searchCategories(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return Collections.emptyList();
        return categoryDAO.searchByName(keyword.trim());
    }

    public Category findByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        return categoryDAO.findByName(name.trim());
    }

}
