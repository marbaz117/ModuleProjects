package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // Default: Production DB (MySQL)
    private static String url = "jdbc:mysql://localhost:3306/expenseProjJDBC";
    private static String user = "root";
    private static String password = "arbaz123456";

    // 🔹 Used only for testing (H2)
    public static void setTestDatabase(String testUrl) {
        url = testUrl;
        user = "sa";
        password = "";
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}