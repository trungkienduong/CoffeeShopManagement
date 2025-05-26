package BUS;

import DAO.ImportLogDAO;
import MODEL.ImportLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Lớp nghiệp vụ xử lý các logic liên quan đến phiếu nhập kho (IMPORT_LOG).
 */
public class ImportLogBUS {
    private static ImportLogBUS instance;
    private final ImportLogDAO importLogDAO;

    private ImportLogBUS() {
        importLogDAO = ImportLogDAO.getInstance();
    }

    public static ImportLogBUS getInstance() {
        if (instance == null) {
            instance = new ImportLogBUS();
        }
        return instance;
    }

    /**
     * Thêm mới một phiếu nhập sau khi kiểm tra hợp lệ.
     */
    public boolean addImportLog(ImportLog log) {
        if (!isValid(log)) {
            System.err.println("Dữ liệu ImportLog không hợp lệ!");
            return false;
        }
        return importLogDAO.insert(log);
    }

    /**
     * Cập nhật thông tin phiếu nhập kho.
     */
    public boolean updateImportLog(ImportLog log) {
        if (!isValid(log) || log.getImportId() == null || log.getImportId() <= 0) {
            System.err.println("Dữ liệu ImportLog không hợp lệ hoặc thiếu ID!");
            return false;
        }
        return importLogDAO.update(log);
    }

    /**
     * Xoá phiếu nhập theo ID.
     */
    public boolean deleteImportLog(int importId) {
        if (importId <= 0) {
            System.err.println("ID phiếu nhập không hợp lệ!");
            return false;
        }
        return importLogDAO.delete(importId);
    }

    /**
     * Lấy toàn bộ danh sách phiếu nhập.
     */
    public List<ImportLog> getAllImportLogs() {
        return importLogDAO.selectAll();
    }

    /**
     * Tìm phiếu nhập theo ID.
     */
    public ImportLog getImportLogById(Integer importId) {
        if (importId == null || importId <= 0) return null;
        return importLogDAO.findById(importId);
    }

    /**
     * Kiểm tra dữ liệu ImportLog có hợp lệ hay không.
     */
    private boolean isValid(ImportLog log) {
        if (log == null) return false;
        if (log.getItemName() == null || log.getItemName().trim().isEmpty()) return false;
        if (log.getIngredientCategoryId() <= 0) return false;
        if (log.getUnitId() <= 0) return false;
        if (log.getSupplierId() <= 0) return false;
        if (log.getQuantity() == null || log.getQuantity().compareTo(BigDecimal.ZERO) <= 0) return false;
        if (log.getUnitPrice() == null || log.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) return false;
        if (log.getImportDate() == null || log.getImportDate().isAfter(LocalDate.now())) return false;
        return true;
    }
}
