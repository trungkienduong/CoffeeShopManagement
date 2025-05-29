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

    public boolean insertCategory(UnitCategory unitCategory) {
        if (isUnitCategoryUnique(unitCategory.getUnitName())) {
            return unitCategoryDAO.insert(unitCategory);
        }
        return false;
    }

    public boolean updateCategory(UnitCategory unitCategory) {
        return unitCategoryDAO.update(unitCategory);
    }

    public boolean canDeleteUnitCategory(int unitId) {
        UnitCategory unitCategory = unitCategoryDAO.findById(unitId);
        return unitCategory != null && unitCategory.getUnitId() != DEFAULT_UNIT_ID;
    }

    public boolean deleteById(int unitId) {
        if (canDeleteUnitCategory(unitId)) {
            return unitCategoryDAO.delete(unitId);
        }
        return false;
    }

    public List<UnitCategory> getAll() {
        return unitCategoryDAO.getAll();
    }

    public UnitCategory findByName(String unitName) {
        return unitCategoryDAO.findByName(unitName);
    }

    public UnitCategory findById(int unitId) {
        return unitCategoryDAO.findById(unitId);
    }

    public boolean isUnitCategoryUnique(String unitName) {
        return unitCategoryDAO.findByName(unitName) == null;
    }
}