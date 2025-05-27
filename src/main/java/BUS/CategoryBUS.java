package BUS;

import DAO.CategoryDAO;
import MODEL.Category;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryBUS {
    private static CategoryBUS instance;
    private final CategoryDAO categoryDAO;
    private static final Logger LOGGER = Logger.getLogger(CategoryBUS.class.getName());

    private CategoryBUS() {
        categoryDAO = CategoryDAO.getInstance();
    }

    public static CategoryBUS getInstance() {
        if (instance == null) {
            instance = new CategoryBUS();
        }
        return instance;
    }

    // ✅ Hàm dùng trực tiếp từ controller khi đã có đối tượng Category
    public boolean insertCategory(Category category) {
        if (category == null || category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            LOGGER.warning("Tên loại sản phẩm không hợp lệ.");
            return false;
        }

        // Kiểm tra trùng tên
        if (categoryDAO.findByName(category.getCategoryName().trim()) != null) {
            LOGGER.warning("Tên loại sản phẩm đã tồn tại.");
            return false;
        }

        return categoryDAO.insert(category);
    }

    // ✅ Hàm cũ - tạo category từ chuỗi tên
    public boolean addCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            LOGGER.warning("Tên loại sản phẩm không được để trống.");
            return false;
        }

        if (categoryDAO.findByName(categoryName.trim()) != null) {
            LOGGER.warning("Tên loại sản phẩm đã tồn tại.");
            return false;
        }

        Category category = new Category(categoryName.trim());
        return categoryDAO.insert(category);
    }

    public boolean updateCategory(int id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            LOGGER.warning("Tên loại sản phẩm không được để trống.");
            return false;
        }

        Category existing = categoryDAO.findById(id);
        if (existing == null) {
            LOGGER.warning("Không tìm thấy loại sản phẩm để cập nhật.");
            return false;
        }

        Category byName = categoryDAO.findByName(newName.trim());
        if (byName != null && byName.getCategoryId() != id) {
            LOGGER.warning("Tên loại mới đã tồn tại.");
            return false;
        }

        existing.setCategoryName(newName.trim());
        return categoryDAO.update(existing);
    }

    public boolean deleteCategory(int id) {
        Category category = categoryDAO.findById(id);
        if (category == null) {
            LOGGER.warning("Không tìm thấy loại sản phẩm để xóa.");
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
