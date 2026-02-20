package com.servlet;

import com.db.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

//@WebServlet("/delete-account")
public class DeleteAccountServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int userId = (Integer) session.getAttribute("userId");
        String password = request.getParameter("password");
        
        try (Connection con = DBConnection.getConnection()) {
            
            // Verify password before deletion
            String checkSql = "SELECT password FROM users WHERE id = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, userId);
            ResultSet rs = checkPs.executeQuery();
            
            if(rs.next()) {
                String dbPassword = rs.getString("password");
                
                if(org.mindrot.jbcrypt.BCrypt.checkpw(password, dbPassword)) {
                    // Delete user
                    String deleteSql = "DELETE FROM users WHERE id = ?";
                    PreparedStatement deletePs = con.prepareStatement(deleteSql);
                    deletePs.setInt(1, userId);
                    deletePs.executeUpdate();
                    
                    // Delete reset tokens if any
                    String tokenSql = "DELETE FROM password_reset_tokens WHERE user_id = ?";
                    PreparedStatement tokenPs = con.prepareStatement(tokenSql);
                    tokenPs.setInt(1, userId);
                    tokenPs.executeUpdate();
                    
                    // Invalidate session
                    session.invalidate();
                    
                    response.sendRedirect("login.jsp?deleted=true");
                    
                } else {
                    response.sendRedirect("delete-account.jsp?error=incorrect");
                }
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("delete-account.jsp?error=failed");
        }
    }
}