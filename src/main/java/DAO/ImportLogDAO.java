package DAO;

import MODEL.ImportLog;
import MODEL.Supplier;
import MODEL.Inventory;
import MODEL.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImportLogDAO {
    private static ImportLogDAO instance;
    private SupplierDAO supplierDAO;
    private InventoryDAO inventoryDAO;
    private EmployeeDAO employeeDAO;

    private ImportLogDAO() {
        supplierDAO = SupplierDAO.getInstance();
        inventoryDAO = InventoryDAO.getInstance();
        employeeDAO = EmployeeDAO.getInstance();
    }

    public static ImportLogDAO getInstance() {
        if (instance == null) {
            instance = new ImportLogDAO();
        }
        return instance;
    }

    // INSERT
    public boolean insert(ImportLog log) {
        String sql = "INSERT INTO IMPORT_LOG (ITEM_ID, SUPPLIER_ID, IMPORT_DATE, QUANTITY, UNIT_PRICE, EMPLOYEE_ID, NOTE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, log.getItem().getItemId());
            pst.setInt(2, log.getSupplier().getSupplierId());
            pst.setDate(3, java.sql.Date.valueOf(log.getImportDate()));
            pst.setBigDecimal(4, log.getQuantity());
            pst.setBigDecimal(5, log.getUnitPrice());

            if (log.getEmployee() != null) {
                pst.setInt(6, log.getEmployee().getEmployeeId());
            } else {
                pst.setNull(6, java.sql.Types.INTEGER);
            }

            pst.setString(7, log.getNote());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean update(ImportLog log) {
        String sql = "UPDATE IMPORT_LOG SET ITEM_ID = ?, SUPPLIER_ID = ?, IMPORT_DATE = ?, QUANTITY = ?, UNIT_PRICE = ?, EMPLOYEE_ID = ?, NOTE = ? " +
                "WHERE IMPORT_ID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, log.getItem().getItemId());
            pst.setInt(2, log.getSupplier().getSupplierId());
            pst.setDate(3, java.sql.Date.valueOf(log.getImportDate()));
            pst.setBigDecimal(4, log.getQuantity());
            pst.setBigDecimal(5, log.getUnitPrice());

            if (log.getEmployee() != null) {
                pst.setInt(6, log.getEmployee().getEmployeeId());
            } else {
                pst.setNull(6, java.sql.Types.INTEGER);
            }

            pst.setString(7, log.getNote());
            pst.setInt(8, log.getImportId());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean delete(int importId) {
        String sql = "DELETE FROM IMPORT_LOG WHERE IMPORT_ID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, importId);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // SELECT ALL
    public List<ImportLog> selectAll() {
        String sql = "SELECT * FROM IMPORT_LOG";
        List<ImportLog> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ImportLog log = new ImportLog();

                log.setImportId(rs.getInt("IMPORT_ID"));

                int itemId = rs.getInt("ITEM_ID");
                Inventory item = inventoryDAO.findById(itemId);
                log.setItem(item);

                int supplierId = rs.getInt("SUPPLIER_ID");
                Supplier supplier = supplierDAO.findById(supplierId);
                log.setSupplier(supplier);

                log.setImportDate(rs.getDate("IMPORT_DATE").toLocalDate());
                log.setQuantity(rs.getBigDecimal("QUANTITY"));
                log.setUnitPrice(rs.getBigDecimal("UNIT_PRICE"));

                int empId = rs.getInt("EMPLOYEE_ID");
                if (!rs.wasNull()) {
                    Employee employee = employeeDAO.getEmployeeById(empId);
                    log.setEmployee(employee);
                } else {
                    log.setEmployee(null);
                }

                log.setNote(rs.getString("NOTE"));

                list.add(log);
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // SELECT BY ID
    public ImportLog findById(int importId) {
        String sql = "SELECT * FROM IMPORT_LOG WHERE IMPORT_ID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, importId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    ImportLog log = new ImportLog();

                    log.setImportId(rs.getInt("IMPORT_ID"));

                    int itemId = rs.getInt("ITEM_ID");
                    Inventory item = inventoryDAO.findById(itemId);
                    log.setItem(item);

                    int supplierId = rs.getInt("SUPPLIER_ID");
                    Supplier supplier = supplierDAO.findById(supplierId);
                    log.setSupplier(supplier);

                    log.setImportDate(rs.getDate("IMPORT_DATE").toLocalDate());
                    log.setQuantity(rs.getBigDecimal("QUANTITY"));
                    log.setUnitPrice(rs.getBigDecimal("UNIT_PRICE"));

                    int empId = rs.getInt("EMPLOYEE_ID");
                    if (!rs.wasNull()) {
                        Employee employee = employeeDAO.getEmployeeById(empId);
                        log.setEmployee(employee);
                    } else {
                        log.setEmployee(null);
                    }

                    log.setNote(rs.getString("NOTE"));

                    return log;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
