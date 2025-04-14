package DAO;

import MODEL.EmployeePosition;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeePositionDAO {
    private static EmployeePositionDAO instance;
    
    private EmployeePositionDAO() {
    }
    
    public static EmployeePositionDAO getInstance() {
        if (instance == null) {
            instance = new EmployeePositionDAO();
        }
        return instance;
    }
    
    // Thêm chức vụ mới
    public boolean insert(EmployeePosition position) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO EmployeePosition (PositionName) VALUES (?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, position.getPositionName());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật thông tin chức vụ
    public boolean update(EmployeePosition position) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE EmployeePosition SET PositionName = ? WHERE PositionId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, position.getPositionName());
            pst.setInt(2, position.getPositionId());
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa chức vụ
    public boolean delete(int positionId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "DELETE FROM EmployeePosition WHERE PositionId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, positionId);
            
            int result = pst.executeUpdate();
            DatabaseConnection.closeConnection(con);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Lấy danh sách tất cả chức vụ
    public List<EmployeePosition> getAll() {
        List<EmployeePosition> positionList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM EmployeePosition";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                EmployeePosition position = new EmployeePosition();
                position.setPositionId(rs.getInt("PositionId"));
                position.setPositionName(rs.getString("PositionName"));
                positionList.add(position);
            }
            
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionList;
    }
    
    // Tìm chức vụ theo ID
    public EmployeePosition findById(int positionId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM EmployeePosition WHERE PositionId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, positionId);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                EmployeePosition position = new EmployeePosition();
                position.setPositionId(rs.getInt("PositionId"));
                position.setPositionName(rs.getString("PositionName"));
                
                DatabaseConnection.closeConnection(con);
                return position;
            }
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Tìm chức vụ theo tên
    public EmployeePosition findByName(String positionName) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM EmployeePosition WHERE PositionName = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, positionName);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                EmployeePosition position = new EmployeePosition();
                position.setPositionId(rs.getInt("PositionId"));
                position.setPositionName(rs.getString("PositionName"));
                
                DatabaseConnection.closeConnection(con);
                return position;
            }
            DatabaseConnection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
