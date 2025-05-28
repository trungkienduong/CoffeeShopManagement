package BUS;

import DAO.UnitCategoryDAO;
import MODEL.UnitCategory;

import java.util.List;

public class UnitCategoryBUS {
    private static UnitCategoryBUS instance;
    private final UnitCategoryDAO unitCategoryDAO;
    private static final int DEFAULT_UNIT_ID = 1;

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
    public boolean insertCategory(UnitCategory unitCategory) {
        if (isUnitCategoryUnique(unitCategory.getUnitName())) {
            return unitCategoryDAO.insert(unitCategory);
        }
        return false; // đã tồn tại
    }

    // -------------------- Update --------------------
    public boolean updateCategory(UnitCategory unitCategory) {
        return unitCategoryDAO.update(unitCategory);
    }

    // -------------------- Can Delete --------------------
    public boolean canDeleteUnitCategory(int unitId) {
        UnitCategory unitCategory = unitCategoryDAO.findById(unitId);
        return unitCategory != null && unitCategory.getUnitId() != DEFAULT_UNIT_ID;
    }

    // -------------------- Delete --------------------
    public boolean deleteById(int unitId) {
        if (canDeleteUnitCategory(unitId)) {
            return unitCategoryDAO.delete(unitId);
        }
        return false;
    }

    // -------------------- Get All --------------------
    public List<UnitCategory> getAll() {
        return unitCategoryDAO.getAll();
    }

    // -------------------- Find By Name --------------------
    public UnitCategory findByName(String unitName) {
        return unitCategoryDAO.findByName(unitName);
    }

    // -------------------- Find By ID --------------------
    public UnitCategory findById(int unitId) {
        return unitCategoryDAO.findById(unitId);
    }

    // -------------------- Check Unique --------------------
    public boolean isUnitCategoryUnique(String unitName) {
        return unitCategoryDAO.findByName(unitName) == null;
    }
}