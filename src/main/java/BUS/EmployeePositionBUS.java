package BUS;

import DAO.EmployeePositionDAO;
import MODEL.EmployeePosition;

import java.util.List;

public class EmployeePositionBUS {
    private static EmployeePositionBUS instance;
    private final EmployeePositionDAO employeePositionDAO;

    private EmployeePositionBUS() {
        employeePositionDAO = EmployeePositionDAO.getInstance();
    }

    public static EmployeePositionBUS getInstance() {
        if (instance == null) {
            instance = new EmployeePositionBUS();
        }
        return instance;
    }

    // ----------------- INSERT -----------------
    public boolean insertEmployeePosition(EmployeePosition employeePosition) {
        if (employeePosition != null && employeePosition.getPositionName() != null && !employeePosition.getPositionName().isEmpty()) {
            try {
                return employeePositionDAO.insert(employeePosition);
            } catch (Exception e) {
                System.err.println("Insert error: " + e.getMessage());
            }
        } else {
            System.out.println("Position name cannot be empty.");
        }
        return false;
    }

    // ----------------- UPDATE -----------------
    public boolean updateEmployeePosition(EmployeePosition employeePosition) {
        if (employeePosition != null && employeePosition.getPositionName() != null && !employeePosition.getPositionName().isEmpty()) {
            try {
                return employeePositionDAO.update(employeePosition);
            } catch (Exception e) {
                System.err.println("Update error: " + e.getMessage());
            }
        } else {
            System.out.println("Position name cannot be empty.");
        }
        return false;
    }

    // ----------------- DELETE -----------------
    // (Cẩn thận) Xóa tất cả chức vụ
    public boolean deleteEmployeePosition(int positionId) {
        try {
            return employeePositionDAO.delete(positionId);
        } catch (Exception e) {
            System.err.println("Delete position error: " + e.getMessage());
        }
        return false;
    }


    // ----------------- GET / FIND -----------------
    public List<EmployeePosition> GetAll() {
        return employeePositionDAO.getAll();
    }

    public EmployeePosition FindByName(String positionName) {
        return employeePositionDAO.findByName(positionName);
    }

    public EmployeePosition FindById(int positionId) {
        return employeePositionDAO.findById(positionId);
    }
}
