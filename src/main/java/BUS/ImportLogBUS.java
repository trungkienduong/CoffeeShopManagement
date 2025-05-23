package BUS;

import DAO.ImportLogDAO;
import MODEL.ImportLog;

import java.util.List;

public class ImportLogBUS {
    private ImportLogDAO importLogDAO;

    public ImportLogBUS() {
        importLogDAO = ImportLogDAO.getInstance();
    }

    // Lấy tất cả bản ghi nhập kho
    public List<ImportLog> getAllImportLogs() {
        return importLogDAO.selectAll();
    }

    // Thêm mới bản ghi nhập kho
    public boolean addImportLog(ImportLog log) {
        if (log == null) {
            System.out.println("ImportLog is null!");
            return false;
        }
        // Có thể thêm kiểm tra nghiệp vụ ở đây, ví dụ quantity phải > 0
        if (log.getQuantity() == null || log.getQuantity().doubleValue() <= 0) {
            System.out.println("Quantity phải lớn hơn 0");
            return false;
        }
        return importLogDAO.insert(log);
    }

    // Cập nhật bản ghi nhập kho
    public boolean updateImportLog(ImportLog log) {
        if (log == null || log.getImportId() <= 0) {
            System.out.println("ImportLog hoặc ImportId không hợp lệ!");
            return false;
        }
        return importLogDAO.update(log);
    }

    // Xóa bản ghi nhập kho theo ID
    public boolean deleteImportLog(int importId) {
        if (importId <= 0) {
            System.out.println("ImportId không hợp lệ!");
            return false;
        }
        return importLogDAO.delete(importId);
    }

    // Tìm kiếm bản ghi nhập kho theo ID
    public ImportLog getImportLogById(int importId) {
        if (importId <= 0) {
            System.out.println("ImportId không hợp lệ!");
            return null;
        }
        return importLogDAO.findById(importId);
    }
}
