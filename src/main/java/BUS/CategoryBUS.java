package BUS;

import DAO.CategoryDAO;
import MODEL.Category;

import java.util.List;

public class CategoryBUS {
    private CategoryDAO categoryDAO;

    public CategoryBUS() {
        categoryDAO = CategoryDAO.getInstance();
    }

    // Lấy danh sách tất cả loại sản phẩm
    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }

    // Thêm loại sản phẩm mới
    public boolean addCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            System.out.println("Tên loại không được để trống");
            return false;
        }

        // Kiểm tra trùng tên
        if (categoryDAO.findByName(categoryName) != null) {
            System.out.println("Tên loại đã tồn tại");
            return false;
        }

        Category category = new Category(categoryName.trim());
        return categoryDAO.insert(category);
    }

    // Cập nhật loại sản phẩm
    public boolean updateCategory(int id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            System.out.println("Tên loại không được để trống");
            return false;
        }

        Category existing = categoryDAO.findById(id);
        if (existing == null) {
            System.out.println("Không tìm thấy loại sản phẩm để cập nhật");
            return false;
        }

        // Kiểm tra nếu tên mới trùng với tên của loại khác
        Category byName = categoryDAO.findByName(newName.trim());
        if (byName != null && byName.getCategoryId() != id) {
            System.out.println("Tên loại mới đã tồn tại");
            return false;
        }

        existing.setCategoryName(newName.trim());
        return categoryDAO.update(existing);
    }

    // Xóa loại sản phẩm
    public boolean deleteCategory(int id) {
        Category category = categoryDAO.findById(id);
        if (category == null) {
            System.out.println("Không tìm thấy loại để xóa");
            return false;
        }

        return categoryDAO.delete(id);
    }

    // Tìm theo ID
    public Category getCategoryById(int id) {
        return categoryDAO.findById(id);
    }

    // Tìm theo tên chính xác
    public Category getCategoryByName(String name) {
        return categoryDAO.findByName(name);
    }

    // Tìm theo tên gần đúng (LIKE)
    public List<Category> searchCategories(String keyword) {
        return categoryDAO.searchByName(keyword);
    }
}
