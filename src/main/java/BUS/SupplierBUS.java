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
        loadSuppliers();
    }

    public static SupplierBUS getInstance() {
        if (instance == null) {
            instance = new SupplierBUS();
        }
        return instance;
    }

    public void loadSuppliers() {
        supplierList = supplierDAO.selectAll();
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public boolean addSupplier(Supplier supplier) {
        boolean success = supplierDAO.insert(supplier);
        if (success) {
            loadSuppliers();
        }
        return success;
    }

    public boolean updateSupplier(Supplier supplier) {
        boolean success = supplierDAO.update(supplier);
        if (success) {
            loadSuppliers();
        }
        return success;
    }

    public boolean deleteSupplier(int supplierId) {
        boolean success = supplierDAO.delete(supplierId);
        if (success) {
            loadSuppliers();
        }
        return success;
    }

    public Supplier findByName(String name) {
        return supplierDAO.findByName(name);
    }

    public Supplier findById(int id) {
        return supplierDAO.findById(id);
    }
}