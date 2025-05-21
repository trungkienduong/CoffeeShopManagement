package BUS;

import DAO.SupplierDAO;
import MODEL.Supplier;

import java.util.ArrayList;
import java.util.List;

public class SupplierBUS {
    private static SupplierBUS instance;
    private final SupplierDAO supplierDAO;
    private List<Supplier> supplierList;

    private SupplierBUS() {
        supplierDAO = SupplierDAO.getInstance();
        supplierList = new ArrayList<>();
        loadSuppliers(); // nạp dữ liệu vào danh sách khi khởi tạo
    }

    public static SupplierBUS getInstance() {
        if (instance == null) {
            instance = new SupplierBUS();
        }
        return instance;
    }

    // ---------------------- Load toàn bộ danh sách ----------------------
    public void loadSuppliers() {
        supplierList = supplierDAO.selectAll();
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    // ---------------------- Thêm nhà cung cấp ----------------------
    public boolean addSupplier(Supplier supplier) {
        boolean success = supplierDAO.insert(supplier);
        if (success) {
            loadSuppliers(); // cập nhật lại danh sách
        }
        return success;
    }

    // ---------------------- Cập nhật nhà cung cấp ----------------------
    public boolean updateSupplier(Supplier supplier) {
        boolean success = supplierDAO.update(supplier);
        if (success) {
            loadSuppliers();
        }
        return success;
    }

    // ---------------------- Xóa nhà cung cấp ----------------------
    public boolean deleteSupplier(int supplierId) {
        boolean success = supplierDAO.delete(supplierId);
        if (success) {
            loadSuppliers();
        }
        return success;
    }

    // ---------------------- Tìm theo tên ----------------------
    public Supplier findByName(String name) {
        return supplierDAO.findByName(name);
    }

    // ---------------------- Tìm theo ID ----------------------
    public Supplier findById(int id) {
        return supplierDAO.findById(id);
    }
}
