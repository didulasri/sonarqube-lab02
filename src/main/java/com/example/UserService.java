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
    
    private static final String DB_URL = System.getenv("DB_URL") != null ? 
        System.getenv("DB_URL") : "jdbc:mysql://localhost/db";
    private static final String DB_USER = System.getenv("DB_USER") != null ? 
        System.getenv("DB_USER") : "root";
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD") != null ? 
        System.getenv("DB_PASSWORD") : "";

    public void findUser(String username) {
        // FIXED: Don't use SELECT * - specify column names
        String query = "SELECT id, name, email FROM users WHERE name = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    logger.log(Level.INFO, "User found: {0}", username);
                } else {
                    logger.log(Level.INFO, "User not found: {0}", username);
                }
            }
        } catch (SQLException e) {
          logger.log(
    Level.SEVERE,
    "Error finding user: {0}",
    new Object[]{username}
);

        }
    }

    public void deleteUser(String username) {
    String query = "DELETE FROM users WHERE name = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, username);
        int rowsAffected = pstmt.executeUpdate();

        logger.log(
            Level.INFO,
            "Deleted {0} user(s) with username: {1}",
            new Object[]{rowsAffected, username}
        );

    } catch (SQLException e) {
       logger.log(
    Level.SEVERE,
    "Error deleting user: {0}",
    new Object[]{username}
);
logger.log(Level.SEVERE, "Exception:", e);

    }
}

}