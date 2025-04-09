package DAO;

import MODEL.EmployeePosition;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePositionDAO {

    // Lấy tất cả chức vụ
    public List<EmployeePosition> getAllPositions() {
        List<EmployeePosition> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM EMPLOYEE_POSITION";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("POSITION_ID");
                String name = rs.getString("POSITION_NAME");
                list.add(new EmployeePosition(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách chức vụ: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }

        return list;
    }

    // Thêm chức vụ mới
    public boolean insertPosition(EmployeePosition position) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO EMPLOYEE_POSITION (POSITION_NAME) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, position.getPosition_Name());

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chức vụ: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }

        return success;
    }

    // Cập nhật tên chức vụ
    public boolean updatePosition(EmployeePosition position) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE EMPLOYEE_POSITION SET POSITION_NAME = ? WHERE POSITION_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, position.getPosition_Name());
            stmt.setInt(2, position.getPosition_ID());

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chức vụ: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }

        return success;
    }

    // Xóa chức vụ theo ID
    public boolean deletePosition(int positionID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM EMPLOYEE_POSITION WHERE POSITION_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, positionID);

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chức vụ: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }

        return success;
    }
}
