package com.servlet;

import com.db.DBConnection;
import com.util.EmailUtility;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        
        // Email validation
        if(email == null || email.trim().isEmpty()) {
            response.sendRedirect("forgot-password.jsp?error=invalid");
            return;
        }
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Get database connection
            con = DBConnection.getConnection();
            
            // Check if email exists in users table
            String checkSql = "SELECT id FROM users WHERE email = ?";
            ps = con.prepareStatement(checkSql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // User found - get user ID
                int userId = rs.getInt("id");
                
                // Generate unique token
                String token = UUID.randomUUID().toString();
                
                // Delete any existing tokens for this user
                String deleteSql = "DELETE FROM password_reset_tokens WHERE user_id = ?";
                PreparedStatement deletePs = con.prepareStatement(deleteSql);
                deletePs.setInt(1, userId);
                deletePs.executeUpdate();
                deletePs.close();
                
                // Insert new token (expires in 1 hour)
                String insertSql = "INSERT INTO password_reset_tokens (user_id, token, expiry_date) "
                                 + "VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 1 HOUR))";
                PreparedStatement insertPs = con.prepareStatement(insertSql);
                insertPs.setInt(1, userId);
                insertPs.setString(2, token);
                insertPs.executeUpdate();
                insertPs.close();
                
                // Create reset link
                String resetLink = "http://localhost:8080/Login/reset-password.jsp?token=" + token;
                
                // Send email
                EmailUtility.sendResetEmail(email, resetLink);
                
                // Redirect with success message
                response.sendRedirect("forgot-password.jsp?success=true");
                
            } else {
                // Email not found in database
                response.sendRedirect("forgot-password.jsp?error=notfound");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("forgot-password.jsp?error=server");
        } finally {
            // Close resources
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(con != null) con.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to the forgot password page
        response.sendRedirect("forgot-password.jsp");
    }
}