package BUS;

import DAO.CategoryDAO;
import MODEL.Category;

import java.util.List;

public class CategoryBUS {
    private final CategoryDAO categoryDAO;

    public CategoryBUS() {
        categoryDAO = CategoryDAO.getInstance();
    }

    // ---------------------- INSERT ----------------------
    public boolean addCategory(Category category) {
        if (category == null || category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            System.out.println("Tên danh mục không được để trống!");
            return false;
        }
        return categoryDAO.insert(category);
    }

    // ---------------------- UPDATE ----------------------
    public boolean updateCategory(Category category) {
        if (category == null || category.getCategoryId() <= 0) {
            System.out.println("ID danh mục không hợp lệ!");
            return false;
        }
        return categoryDAO.update(category);
    }

    // ---------------------- DELETE ----------------------
    public boolean deleteCategory(int categoryId) {
        if (categoryId <= 0) {
            System.out.println("ID danh mục không hợp lệ!");
            return false;
        }
        return categoryDAO.delete(categoryId);
    }

    // ---------------------- GET ALL ----------------------
    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }

    // ---------------------- FIND BY ID ----------------------
    public Category getCategoryById(int categoryId) {
        if (categoryId <= 0) return null;
        return categoryDAO.findById(categoryId);
    }

    // ---------------------- FIND BY NAME ----------------------
    public Category getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        return categoryDAO.findByName(name);
    }

    // ---------------------- FIND BY TYPE ----------------------
    public List<Category> getCategoriesByType(char type) {
        return categoryDAO.findByType(type);
    }

    // ---------------------- SEARCH BY NAME ----------------------
    public List<Category> searchCategoriesByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return null;
        return categoryDAO.searchByName(keyword);
    }
}
