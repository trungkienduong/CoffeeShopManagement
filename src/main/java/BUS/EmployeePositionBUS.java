package BUS;

import DAO.EmployeePositionDAO;
import MODEL.EmployeePosition;

import java.util.List;

public class EmployeePositionBUS {
    private final EmployeePositionDAO dao;

    public EmployeePositionBUS() {
        this.dao = EmployeePositionDAO.getInstance();
    }

    public List<EmployeePosition> getAllPositions() {
        return dao.selectAll();
    }

    public EmployeePosition getPositionById(int id) {
        return dao.findById(id);
    }

    public boolean addPosition(EmployeePosition position) {
        if (position == null || position.getPositionName() == null || position.getPositionName().isEmpty()) {
            return false;
        }

        return dao.insert(position);
    }

    public boolean updatePosition(EmployeePosition position) {
        if (position == null || position.getPositionId() <= 0 || position.getPositionName() == null || position.getPositionName().isEmpty()) {
            return false;
        }

        return dao.update(position);
    }

    public boolean deletePosition(int id) {
        return dao.delete(id);
    }

    public List<EmployeePosition> searchPositionByName(String keyword) {
        return dao.selectAll().stream()
                .filter(p -> p.getPositionName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
