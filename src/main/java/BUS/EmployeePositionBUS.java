package BUS;

import DAO.EmployeePositionDAO;
import MODEL.EmployeePosition;
import javafx.collections.ObservableList;

import java.util.List;

public class EmployeePositionBUS {
    private final EmployeePositionDAO dao = new EmployeePositionDAO();

    // Lấy danh sách chức vụ
    public List<EmployeePosition> getAllPositions() {
        return dao.getAllPositions();
    }

    // Lấy danh sách chức vụ cho ComboBox
    public ObservableList<EmployeePosition> getObservablePositions() {
        return dao.getObservablePositions();
    }

    // Tìm chức vụ theo ID
    public EmployeePosition getPositionById(int id) {
        return dao.findById(id);
    }

    // Thêm mới
    public boolean addPosition(EmployeePosition position) {
        if (position.getPosition_Name() == null || position.getPosition_Name().trim().isEmpty()) {
            System.err.println("Tên chức vụ không được để trống.");
            return false;
        }
        return dao.insertPosition(position);
    }

    // Cập nhật
    public boolean updatePosition(EmployeePosition position) {
        if (position.getPosition_Name() == null || position.getPosition_Name().trim().isEmpty()) {
            System.err.println("Tên chức vụ không được để trống.");
            return false;
        }
        return dao.updatePosition(position);
    }

    // Xóa
    public boolean deletePosition(int id) {
        return dao.deletePosition(id);
    }
}
