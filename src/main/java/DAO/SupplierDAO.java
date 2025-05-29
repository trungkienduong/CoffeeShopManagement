package DAO;

import MODEL.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    private static SupplierDAO instance;

    public static SupplierDAO getInstance() {
        if (instance == null) {
            instance = new SupplierDAO();
        }
        return instance;
    }

    public boolean insert(Supplier supplier) {
        String sql = "INSERT INTO SUPPLIER (SUPPLIER_NAME, PHONE, ADDRESS, EMAIL) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getPhone());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getEmail());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Supplier supplier) {
        String sql = "UPDATE SUPPLIER SET SUPPLIER_NAME = ?, PHONE = ?, ADDRESS = ?, EMAIL = ? WHERE SUPPLIER_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getPhone());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getEmail());
            pst.setInt(5, supplier.getSupplierId());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int supplierId) {
        String sql = "DELETE FROM SUPPLIER WHERE SUPPLIER_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, supplierId);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Supplier findById(int supplierId) {
        String sql = "SELECT * FROM SUPPLIER WHERE SUPPLIER_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, supplierId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return extractSupplier(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Supplier findByName(String name) {
        String sql = "SELECT * FROM SUPPLIER WHERE SUPPLIER_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return extractSupplier(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Supplier> getAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM SUPPLIER";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(extractSupplier(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Supplier> selectAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM [SUPPLIER]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(extractSupplier(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    private Supplier extractSupplier(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(rs.getInt("SUPPLIER_ID"));
        supplier.setSupplierName(rs.getString("SUPPLIER_NAME"));
        supplier.setPhone(rs.getString("PHONE"));
        supplier.setAddress(rs.getString("ADDRESS"));
        supplier.setEmail(rs.getString("EMAIL"));
        return supplier;
    }
}
