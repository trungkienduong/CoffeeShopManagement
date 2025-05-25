package BUS;

import DAO.ImportLogDAO;
import MODEL.ImportLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
     * Thêm mới phiếu nhập sau khi kiểm tra hợp lệ dữ liệu.
     */
    public boolean addImportLog(ImportLog log) {
        if (!isValid(log)) {
            System.err.println("Dữ liệu ImportLog không hợp lệ!");
            return false;
        }
        return importLogDAO.insert(log);
    }

    /**
     * Cập nhật thông tin phiếu nhập.
     */
    public boolean updateImportLog(ImportLog log) {
        if (!isValid(log) || log.getImportId() <= 0) {
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
     * Lấy danh sách toàn bộ phiếu nhập.
     */
    public List<ImportLog> getAllImportLogs() {
        return importLogDAO.selectAll();
    }

    /**
     * Tìm phiếu nhập theo ID.
     */
    public ImportLog getImportLogById(int importId) {
        if (importId <= 0) return null;
        return importLogDAO.findById(importId);
    }

    /**
     * Kiểm tra dữ liệu ImportLog hợp lệ hay không.
     */
    private boolean isValid(ImportLog log) {
        if (log == null) return false;
        if (log.getItemName() == null || log.getItemName().trim().isEmpty()) return false;
        if (log.getCategoryId() <= 0 || log.getUnitId() <= 0 || log.getSupplierId() <= 0) return false;
        if (log.getQuantity() == null || log.getQuantity().compareTo(BigDecimal.ZERO) <= 0) return false;
        if (log.getUnitPrice() == null || log.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) return false;
        if (log.getImportDate() == null || log.getImportDate().isAfter(LocalDate.now())) return false;
        return true;
    }
}
