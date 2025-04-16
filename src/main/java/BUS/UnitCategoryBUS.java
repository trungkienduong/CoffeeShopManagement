package BUS;

import DAO.UnitCategoryDAO;
import MODEL.UnitCategory;
import java.util.List;

public class UnitCategoryBUS {
    private static UnitCategoryBUS instance;
    private UnitCategoryDAO unitCategoryDAO;

    private static final int DEFAULT_UNIT_ID = 1; // Đơn vị mặc định

    private UnitCategoryBUS() {
        unitCategoryDAO = UnitCategoryDAO.getInstance();
    }

    public static UnitCategoryBUS getInstance() {
        if (instance == null) {
            instance = new UnitCategoryBUS();
        }
        return instance;
    }

    // -------------------- Insert --------------------
    public void insertCategory(UnitCategory unitCategory) {
        if (isUnitCategoryUnique(unitCategory.getUnitName())) {
            try {
                unitCategoryDAO.insert(unitCategory);
            } catch (Exception e) {
                System.err.println("Error inserting unit category: " + e.getMessage());
            }
        } else {
            System.out.println("Unit category already exists.");
        }
    }

    // -------------------- Update --------------------
    public void updateCategory(UnitCategory unitCategory) {
        try {
            unitCategoryDAO.update(unitCategory);
        } catch (Exception e) {
            System.err.println("Error updating unit category: " + e.getMessage());
        }
    }

    // -------------------- Can Delete --------------------
    public boolean canDeleteUnitCategory(int unitId) {
        UnitCategory unitCategory = unitCategoryDAO.findById(unitId);
        if (unitCategory == null) {
            return false; // Unit category does not exist
        }
        // Kiểm tra xem unit category có phải là unit category mặc định hay không
        return unitCategory.getUnitId() != DEFAULT_UNIT_ID;
    }

    // -------------------- Delete --------------------
    public void deleteId(int unitId) {
        if (canDeleteUnitCategory(unitId)) {
            try {
                unitCategoryDAO.delete(unitId);
            } catch (Exception e) {
                System.err.println("Error deleting unit category: " + e.getMessage());
            }
        } else {
            System.out.println("Cannot delete the default unit category.");
        }
    }

    // -------------------- Get All --------------------
    public List<UnitCategory> GetAll() {
        return unitCategoryDAO.getAll();
    }

    // -------------------- Find By Name --------------------
    public UnitCategory FindByName(String unitName) {
        return unitCategoryDAO.findByName(unitName);
    }

    // -------------------- Find By ID --------------------
    public UnitCategory FindById(int unitId) {
        return unitCategoryDAO.findById(unitId);
    }

    // -------------------- Check Category --------------------
    public boolean isUnitCategoryUnique(String unitName) {
        return unitCategoryDAO.findByName(unitName) == null;
    }
}
