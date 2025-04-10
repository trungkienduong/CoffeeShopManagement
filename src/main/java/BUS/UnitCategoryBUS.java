package BUS;

import DAO.UnitCategoryDAO;
import MODEL.UnitCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UnitCategoryBUS {
    private final UnitCategoryDAO dao = new UnitCategoryDAO();

    // Lấy tất cả đơn vị tính
    public List<UnitCategory> getAllUnits() {
        return dao.getAllUnitCategories();
    }

    // Trả về danh sách Observable cho ComboBox, TableView
    public ObservableList<UnitCategory> getObservableUnits() {
        return FXCollections.observableArrayList(dao.getAllUnitCategories());
    }

    // Thêm đơn vị
    public boolean addUnit(UnitCategory unit) {
        if (isValidUnit(unit)) {
            return dao.addUnitCategory(unit);
        }
        return false;
    }

    // Cập nhật đơn vị
    public boolean updateUnit(UnitCategory unit) {
        if (isValidUnit(unit)) {
            return dao.updateUnitCategory(unit);
        }
        return false;
    }

    // Xóa đơn vị
    public boolean deleteUnit(int unitID) {
        return dao.deleteUnitCategory(unitID);
    }

    // Tìm kiếm đơn vị
    public List<UnitCategory> searchUnits(String keyword) {
        return dao.searchUnitCategories(keyword);
    }

    // Kiểm tra đơn vị hợp lệ
    private boolean isValidUnit(UnitCategory unit) {
        if (unit.getUnit_Name() == null || unit.getUnit_Name().trim().isEmpty()) {
            System.err.println("Tên đơn vị không được để trống.");
            return false;
        }
        return true;
    }
}
