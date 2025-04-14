package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlserver://DEATH-KNIGHT:1433;databaseName=CoffeeShopManagement;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "101001";

    // phương thức mở kết nối
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Kết nối với CSDL thanh công!");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối với CSDL: " + e.getMessage());
        }
        return connection;
    }

    // phương thức đóng kết nối
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Đã đóng kết nối!");
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("Kết nối với CSDL thanh công!");
        }
        else {
            System.out.println("Lỗi kết nối với CSDL!");
        }
        closeConnection(connection);
    }
}
