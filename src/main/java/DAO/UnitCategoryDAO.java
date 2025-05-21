package DAO;

import MODEL.UnitCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitCategoryDAO {
    private static UnitCategoryDAO instance;

    private UnitCategoryDAO() {}

    public static UnitCategoryDAO getInstance() {
        if (instance == null) {
            instance = new UnitCategoryDAO();
        }
        return instance;
    }

    /**
     * Thêm một đơn vị mới vào database
     * @param unitCategory đối tượng đơn vị cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean insert(UnitCategory unitCategory) {
        String sql = "INSERT INTO [UNIT_CATEGORY] (UNIT_NAME) VALUES (?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, unitCategory.getUnitName());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật tên đơn vị theo ID
     * @param unitCategory đối tượng đơn vị đã được chỉnh sửa
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean update(UnitCategory unitCategory) {
        String sql = "UPDATE [UNIT_CATEGORY] SET UNIT_NAME = ? WHERE UNIT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, unitCategory.getUnitName());
            pst.setInt(2, unitCategory.getUnitId());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa đơn vị theo ID
     * @param unitId mã đơn vị cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean delete(int unitId) {
        String sql = "DELETE FROM [UNIT_CATEGORY] WHERE UNIT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, unitId);
            int result = pst.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy tất cả đơn vị có trong database
     * @return danh sách đơn vị, trả về danh sách rỗng nếu không có hoặc lỗi
     */
    public List<UnitCategory> getAll() {
        String sql = "SELECT * FROM [UNIT_CATEGORY]";

        List<UnitCategory> unitCategories = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                UnitCategory unitCategory = new UnitCategory();
                unitCategory.setUnitId(rs.getInt("UNIT_ID"));
                unitCategory.setUnitName(rs.getString("UNIT_NAME"));

                unitCategories.add(unitCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unitCategories;
    }

    /**
     * Tìm đơn vị theo tên
     * @param unitName tên đơn vị cần tìm
     * @return đối tượng đơn vị nếu tìm thấy, null nếu không có hoặc lỗi
     */
    public UnitCategory findByName(String unitName) {
        String sql = "SELECT * FROM [UNIT_CATEGORY] WHERE UNIT_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, unitName);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    UnitCategory unitCategory = new UnitCategory();
                    unitCategory.setUnitId(rs.getInt("UNIT_ID"));
                    unitCategory.setUnitName(rs.getString("UNIT_NAME"));
                    return unitCategory;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tìm đơn vị theo ID
     * @param unitId mã đơn vị cần tìm
     * @return đối tượng đơn vị nếu tìm thấy, null nếu không có hoặc lỗi
     */
    public UnitCategory findById(int unitId) {
        String sql = "SELECT * FROM [UNIT_CATEGORY] WHERE UNIT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, unitId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    UnitCategory unitCategory = new UnitCategory();
                    unitCategory.setUnitId(rs.getInt("UNIT_ID"));
                    unitCategory.setUnitName(rs.getString("UNIT_NAME"));
                    return unitCategory;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
