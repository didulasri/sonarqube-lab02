package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    
    // FIXED: Load credentials from environment variables or config
    private static final String DB_URL = System.getenv("DB_URL") != null ? 
        System.getenv("DB_URL") : "jdbc:mysql://localhost/db";
    private static final String DB_USER = System.getenv("DB_USER") != null ? 
        System.getenv("DB_USER") : "root";
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD") != null ? 
        System.getenv("DB_PASSWORD") : "";

    // FIXED: SQL Injection - using PreparedStatement
    // FIXED: Resource management with try-with-resources
    public void findUser(String username) {
        String query = "SELECT * FROM users WHERE name = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    logger.info("User found: " + username);
                } else {
                    logger.info("User not found: " + username);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding user: " + username, e);
        }
    }

    // FIXED: SQL Injection - using PreparedStatement
    // FIXED: Resource management with try-with-resources
    // FIXED: Changed Exception to SQLException
    public void deleteUser(String username) {
        String query = "DELETE FROM users WHERE name = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();
            logger.info("Deleted " + rowsAffected + " user(s) with username: " + username);
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting user: " + username, e);
        }
    }
    
    // REMOVED: notUsed() method - it was never called
}