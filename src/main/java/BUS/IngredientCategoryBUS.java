package BUS;

import DAO.IngredientCategoryDAO;
import MODEL.IngredientCategory;

import java.util.List;

/**
 * BUS xử lý nghiệp vụ liên quan đến loại nguyên liệu (IngredientCategory).
 */
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

    /**
     * Thêm mới loại nguyên liệu, kiểm tra hợp lệ tên.
     */
    public boolean addCategory(IngredientCategory category) {
        if (!isValid(category)) {
            System.err.println("Tên loại nguyên liệu không hợp lệ hoặc trùng.");
            return false;
        }
        return dao.insert(category);
    }

    /**
     * Cập nhật loại nguyên liệu.
     */
    public boolean updateCategory(IngredientCategory category) {
        if (!isValid(category) || category.getIngredientCategoryId() <= 0) {
            System.err.println("Dữ liệu không hợp lệ hoặc thiếu ID.");
            return false;
        }
        return dao.update(category);
    }

    /**
     * Xóa loại nguyên liệu theo ID.
     */
    public boolean deleteCategory(int id) {
        if (id <= 0) {
            System.err.println("ID không hợp lệ.");
            return false;
        }
        return dao.delete(id);
    }

    /**
     * Lấy danh sách toàn bộ loại nguyên liệu.
     */
    public List<IngredientCategory> getAllCategories() {
        return dao.selectAll();
    }

    /**
     * Tìm loại nguyên liệu theo ID.
     */
    public IngredientCategory getCategoryById(int id) {
        if (id <= 0) return null;
        return dao.findById(id);
    }

    /**
     * Kiểm tra tính hợp lệ của dữ liệu loại nguyên liệu.
     * - Tên không null, không rỗng.
     * - Tên không trùng (có thể kiểm tra ở DB nếu cần).
     */
    private boolean isValid(IngredientCategory category) {
        if (category == null) return false;
        String name = category.getIngredientCategoryName();
        if (name == null || name.trim().isEmpty()) return false;

        // Kiểm tra tên đã tồn tại trong DB chưa (có thể thêm nếu muốn)
        // Ví dụ: tìm theo tên xem có bản ghi khác không
        // Đơn giản chưa làm, để tránh lỗi trùng tên cần có cơ chế kiểm tra bên ngoài hoặc trong DB.

        return true;
    }
}
