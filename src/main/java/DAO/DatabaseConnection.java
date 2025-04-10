package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://DEATH-KNIGHT:1433;databaseName=CoffeeShopManagement;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "101001";

    // Phương thức mở kết nối
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối database thành công!");
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối database: " + e.getMessage());
        }
        return conn;
    }

    // Phương thức đóng kết nối
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Đã đóng kết nối database.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Test kết nối thành công!");
        } else {
            System.out.println("Test kết nối thất bại!");
        }
        closeConnection(conn);
    }

}
