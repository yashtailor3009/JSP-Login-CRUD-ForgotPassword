package com.servlet;

import com.db.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validation
        if(token == null || token.trim().isEmpty()) {
            response.sendRedirect("forgot-password.jsp?error=invalid");
            return;
        }
        
        if(newPassword == null || newPassword.trim().isEmpty()) {
            response.sendRedirect("reset-password.jsp?token=" + token + "&error=Password required");
            return;
        }
        
        if(newPassword.length() < 6) {
            response.sendRedirect("reset-password.jsp?token=" + token + "&error=Password must be at least 6 characters");
            return;
        }
        
        if(!newPassword.equals(confirmPassword)) {
            response.sendRedirect("reset-password.jsp?token=" + token + "&error=Passwords do not match");
            return;
        }
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.getConnection();
            
            // Verify token and check expiry
            String verifySql = "SELECT user_id FROM password_reset_tokens "
                             + "WHERE token = ? AND expiry_date > NOW()";
            ps = con.prepareStatement(verifySql);
            ps.setString(1, token);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                
                // Hash the new password
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                
                // Update user's password
                String updateSql = "UPDATE users SET password = ? WHERE id = ?";
                PreparedStatement updatePs = con.prepareStatement(updateSql);
                updatePs.setString(1, hashedPassword);
                updatePs.setInt(2, userId);
                updatePs.executeUpdate();
                updatePs.close();
                
                // Delete the used token
                String deleteSql = "DELETE FROM password_reset_tokens WHERE token = ?";
                PreparedStatement deletePs = con.prepareStatement(deleteSql);
                deletePs.setString(1, token);
                deletePs.executeUpdate();
                deletePs.close();
                
                // Redirect to login with success message
                response.sendRedirect("login.jsp?reset=success");
                
            } else {
                // Token invalid or expired
                response.sendRedirect("forgot-password.jsp?error=expired");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("forgot-password.jsp?error=server");
        } finally {
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
        // Show reset password form
        String token = request.getParameter("token");
        if(token == null || token.trim().isEmpty()) {
            response.sendRedirect("forgot-password.jsp");
            return;
        }
        response.sendRedirect("reset-password.jsp?token=" + token);
    }
}