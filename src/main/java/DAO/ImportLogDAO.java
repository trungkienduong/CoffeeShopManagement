package DAO;

import MODEL.ImportLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác với bảng IMPORT_LOG.
 */
public class ImportLogDAO {
    private static ImportLogDAO instance;

    private ImportLogDAO() {}

    public static ImportLogDAO getInstance() {
        if (instance == null) {
            instance = new ImportLogDAO();
        }
        return instance;
    }

    /**
     * Thêm mới một bản ghi import log.
     */
    public boolean insert(ImportLog log) {
        String sql = "INSERT INTO IMPORT_LOG (ITEM_NAME, CATEGORY_ID, UNIT_ID, SUPPLIER_ID, IMPORT_DATE, " +
                "QUANTITY, UNIT_PRICE, EMPLOYEE_ID, NOTE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, log.getItemName());
            pst.setInt(2, log.getCategoryId());
            pst.setInt(3, log.getUnitId());
            pst.setInt(4, log.getSupplierId());
            pst.setDate(5, Date.valueOf(log.getImportDate()));
            pst.setBigDecimal(6, log.getQuantity());
            pst.setBigDecimal(7, log.getUnitPrice());

            if (log.getEmployeeId() != null) {
                pst.setInt(8, log.getEmployeeId());
            } else {
                pst.setNull(8, Types.INTEGER);
            }

            pst.setString(9, log.getNote());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Insert ImportLog failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cập nhật một bản ghi import log.
     */
    public boolean update(ImportLog log) {
        String sql = "UPDATE IMPORT_LOG SET ITEM_NAME = ?, CATEGORY_ID = ?, UNIT_ID = ?, SUPPLIER_ID = ?, IMPORT_DATE = ?, " +
                "QUANTITY = ?, UNIT_PRICE = ?, EMPLOYEE_ID = ?, NOTE = ? WHERE IMPORT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, log.getItemName());
            pst.setInt(2, log.getCategoryId());
            pst.setInt(3, log.getUnitId());
            pst.setInt(4, log.getSupplierId());
            pst.setDate(5, Date.valueOf(log.getImportDate()));
            pst.setBigDecimal(6, log.getQuantity());
            pst.setBigDecimal(7, log.getUnitPrice());

            if (log.getEmployeeId() != null) {
                pst.setInt(8, log.getEmployeeId());
            } else {
                pst.setNull(8, Types.INTEGER);
            }

            pst.setString(9, log.getNote());
            pst.setInt(10, log.getImportId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update ImportLog failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xóa bản ghi theo ID.
     */
    public boolean delete(int importId) {
        String sql = "DELETE FROM IMPORT_LOG WHERE IMPORT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, importId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete ImportLog failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lấy toàn bộ import log.
     */
    public List<ImportLog> selectAll() {
        String sql = "SELECT * FROM IMPORT_LOG";
        List<ImportLog> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(extractImportLog(rs));
            }

        } catch (SQLException e) {
            System.err.println("Select all ImportLogs failed: " + e.getMessage());
        }

        return list;
    }

    /**
     * Tìm import log theo ID.
     */
    public ImportLog findById(int importId) {
        String sql = "SELECT * FROM IMPORT_LOG WHERE IMPORT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, importId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractImportLog(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Find ImportLog by ID failed: " + e.getMessage());
        }

        return null;
    }

    /**
     * Tạo đối tượng ImportLog từ ResultSet.
     */
    private ImportLog extractImportLog(ResultSet rs) throws SQLException {
        ImportLog log = new ImportLog();

        log.setImportId(rs.getInt("IMPORT_ID"));
        log.setItemName(rs.getString("ITEM_NAME"));
        log.setCategoryId(rs.getInt("CATEGORY_ID"));
        log.setUnitId(rs.getInt("UNIT_ID"));
        log.setSupplierId(rs.getInt("SUPPLIER_ID"));
        log.setImportDate(rs.getDate("IMPORT_DATE").toLocalDate());
        log.setQuantity(rs.getBigDecimal("QUANTITY"));
        log.setUnitPrice(rs.getBigDecimal("UNIT_PRICE"));

        int empId = rs.getInt("EMPLOYEE_ID");
        log.setEmployeeId(rs.wasNull() ? null : empId);

        log.setNote(rs.getString("NOTE"));

        return log;
    }
}
