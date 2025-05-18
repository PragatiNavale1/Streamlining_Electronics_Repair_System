package com.hrms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/hrms";  // Change localhost if needed
    private static final String USER = "root";  // Your MySQL username
    private static final String PASSWORD = "alka@123";  // Your MySQL password

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Return the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Handle any JDBC driver errors
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found.");
        }
    }
}
