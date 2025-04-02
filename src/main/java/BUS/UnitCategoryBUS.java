package BUS;

import DAO.UnitCategoryDAO;
import MODEL.UnitCategory;
import java.sql.Connection;
import java.util.List;

public class UnitCategoryBUS {
    private UnitCategoryDAO unitCategoryDAO;

    public UnitCategoryBUS(Connection connection) {
        this.unitCategoryDAO = new UnitCategoryDAO(connection);
    }

    // Lấy tất cả các đơn vị tính
    public List<UnitCategory> getAllUnits() {
        return unitCategoryDAO.getAllUnits();
    }

    // Lấy thông tin đơn vị tính theo UNIT_ID
    public UnitCategory getUnitByID(int unitID) {
        return unitCategoryDAO.getUnitByID(unitID);
    }

    // Thêm đơn vị tính mới
    public boolean addUnit(UnitCategory unit) {
        if (unit == null || unit.getUnitName().isEmpty()) {
            System.out.println("Thông tin đơn vị tính không hợp lệ.");
            return false;
        }
        return unitCategoryDAO.addUnit(unit);
    }

    // Cập nhật thông tin đơn vị tính
    public boolean updateUnit(UnitCategory unit) {
        if (unit == null || unit.getUnitID() <= 0 || unit.getUnitName().isEmpty()) {
            System.out.println("Dữ liệu đơn vị tính không hợp lệ.");
            return false;
        }
        return unitCategoryDAO.updateUnit(unit);
    }

    // Xóa đơn vị tính theo UNIT_ID
    public boolean deleteUnit(int unitID) {
        if (unitID <= 0) {
            System.out.println("ID đơn vị tính không hợp lệ.");
            return false;
        }
        return unitCategoryDAO.deleteUnit(unitID);
    }
}
