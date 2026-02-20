package com.servlet;

import com.db.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

//@WebServlet("/edit-profile")
public class EditProfileServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int userId = (Integer) session.getAttribute("userId");
        String newUsername = request.getParameter("username");
        String newEmail = request.getParameter("email");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        
        try (Connection con = DBConnection.getConnection()) {
            
            // Verify current password
            String checkSql = "SELECT password FROM users WHERE id = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, userId);
            ResultSet rs = checkPs.executeQuery();
            
            if(rs.next()) {
                String dbPassword = rs.getString("password");
                
                if(!BCrypt.checkpw(currentPassword, dbPassword)) {
                    request.setAttribute("error", "Current password is incorrect");
                    request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
                    return;
                }
            }
            
            // Update user info
            String updateSql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setString(1, newUsername);
            updatePs.setString(2, newEmail);
            updatePs.setInt(3, userId);
            updatePs.executeUpdate();
            
            // Update password if provided
            if(newPassword != null && !newPassword.trim().isEmpty()) {
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                String passSql = "UPDATE users SET password = ? WHERE id = ?";
                PreparedStatement passPs = con.prepareStatement(passSql);
                passPs.setString(1, hashedPassword);
                passPs.setInt(2, userId);
                passPs.executeUpdate();
            }
            
            // Update session with new username
            session.setAttribute("username", newUsername);
            
            response.sendRedirect("profile.jsp?success=updated");
            
        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Update failed: " + e.getMessage());
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
        }
    }
}