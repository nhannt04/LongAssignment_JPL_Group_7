package se.fu.vn.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnect {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            // Nếu chưa có kết nối hoặc kết nối đã đóng thì tạo mới
            if (conn == null || conn.isClosed()) {
                Properties props = new Properties();
                props.load(DBConnect.class.getClassLoader().getResourceAsStream("db.properties"));

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");

                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("✅ Connected to database!");
            }
        } catch (Exception e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
        }

        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
