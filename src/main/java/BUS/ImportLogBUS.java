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

    public boolean addImportLog(ImportLog log) {
        if (!isValid(log)) {
            return false;
        }
        return importLogDAO.insert(log);
    }

    public boolean updateImportLog(ImportLog log) {
        if (!isValid(log) || log.getImportId() == null || log.getImportId() <= 0) {
            return false;
        }
        return importLogDAO.update(log);
    }

    public boolean deleteImportLog(int importId) {
        if (importId <= 0) {
            return false;
        }
        return importLogDAO.delete(importId);
    }

    public List<ImportLog> getAllImportLogs() {
        return importLogDAO.selectAll();
    }

    public ImportLog getImportLogById(Integer importId) {
        if (importId == null || importId <= 0) return null;
        return importLogDAO.findById(importId);
    }

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
