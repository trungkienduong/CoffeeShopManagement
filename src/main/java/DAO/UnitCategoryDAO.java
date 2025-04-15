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

    private UnitCategoryDAO() {

    }

    public static UnitCategoryDAO getInstance() {
        if (instance == null) {
            instance = new UnitCategoryDAO();
        }
        return instance;
    }

    // ---------------------- INSERT ----------------------
    public boolean insert(UnitCategory unitCategory) {
        String sql = "INSERT INTO UNIT_CATEGORY (UNIT_NAME, DESCRIPTION) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, unitCategory.getUnitName()); // gán giá trị cho ? thứ nhất
            pst.setString(2, unitCategory.getDescription()); // gán giá trị cho ? thứ hai

            // thực thi câu lệnh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- UPDATE ----------------------
    public boolean update(UnitCategory unitCategory) {
        String sql = "UPDATE UNIT_CATEGORY SET UNIT_NAME = ?, DESCRIPTION = ? WHERE UNIT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, unitCategory.getUnitName()); // gán giá trị cho ? thứ hai
            pst.setString(2, unitCategory.getDescription()); // gán giá trị cho ? thứ hai
            pst.setInt(3, unitCategory.getUnitId()); // gán giá trị cho ? thứ hai

            // thực thi câu lệnh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- DELETE ----------------------
    public boolean delete(int unitId) {
        String sql = "DELETE FROM UNIT_CATEGORY WHERE UNIT_ID = ?";

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

    // ---------------------- GET ALL ----------------------
    public List<UnitCategory> getAll() {
        String sql = "SELECT * FROM UNIT_CATEGORY";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<UnitCategory> unitCategories = new ArrayList<>();
            while (rs.next()) {
                UnitCategory unitCategory = new UnitCategory();
                unitCategory.setUnitId(rs.getInt("UNIT_ID"));
                unitCategory.setUnitName(rs.getString("UNIT_NAME"));
                unitCategory.setDescription(rs.getString("DESCRIPTION"));
                unitCategories.add(unitCategory);
            }
            return unitCategories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY NAME ----------------------
    public UnitCategory findByName(String unitName) {
        String sql = "SELECT * FROM UNIT_CATEGORY WHERE UNIT_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, unitName); // gán giá trị cho ? thứ nhất
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                UnitCategory unitCategory = new UnitCategory();
                unitCategory.setUnitId(rs.getInt("UNIT_ID"));
                unitCategory.setUnitName(rs.getString("UNIT_NAME"));
                unitCategory.setDescription(rs.getString("DESCRIPTION"));
                return unitCategory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
