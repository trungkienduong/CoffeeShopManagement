package DAO;

import MODEL.UnitCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnitCategoryDAO {

    // Lấy toàn bộ danh sách đơn vị tính
    public List<UnitCategory> getAllUnitCategories() {
        List<UnitCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM UNIT_CATEGORY";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UnitCategory unit = extractUnitCategory(rs);
                list.add(unit);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách UNIT_CATEGORY: " + e.getMessage());
        }

        return list;
    }

    // Thêm đơn vị tính mới
    public boolean addUnitCategory(UnitCategory unit) {
        String sql = "INSERT INTO UNIT_CATEGORY (UNIT_NAME, DESCRIPTION) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, unit.getUnit_Name());
            stmt.setString(2, unit.getDescription());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm UNIT_CATEGORY: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật đơn vị tính
    public boolean updateUnitCategory(UnitCategory unit) {
        String sql = "UPDATE UNIT_CATEGORY SET UNIT_NAME = ?, DESCRIPTION = ? WHERE UNIT_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, unit.getUnit_Name());
            stmt.setString(2, unit.getDescription());
            stmt.setInt(3, unit.getUnit_ID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật UNIT_CATEGORY: " + e.getMessage());
            return false;
        }
    }

    // Xoá đơn vị tính
    public boolean deleteUnitCategory(int unitId) {
        String sql = "DELETE FROM UNIT_CATEGORY WHERE UNIT_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, unitId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xoá UNIT_CATEGORY: " + e.getMessage());
            return false;
        }
    }

    // Tìm kiếm theo tên hoặc mã đơn vị
    public List<UnitCategory> searchUnitCategories(String keyword) {
        List<UnitCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM UNIT_CATEGORY WHERE UNIT_NAME LIKE ? OR CAST(UNIT_ID AS NVARCHAR) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UnitCategory unit = extractUnitCategory(rs);
                list.add(unit);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm UNIT_CATEGORY: " + e.getMessage());
        }

        return list;
    }

    // Hàm hỗ trợ lấy đối tượng từ ResultSet
    private UnitCategory extractUnitCategory(ResultSet rs) throws SQLException {
        return new UnitCategory(
                rs.getInt("UNIT_ID"),
                rs.getString("UNIT_NAME"),
                rs.getString("DESCRIPTION")
        );
    }
}
