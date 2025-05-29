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