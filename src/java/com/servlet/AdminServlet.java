package com.servlet;

import com.db.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("role");
        
        if(role == null || !"admin".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        List<Map<String, String>> users = new ArrayList<>();
        
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT id, name, email FROM users ORDER BY id DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("id", String.valueOf(rs.getInt("id")));
                user.put("name", rs.getString("name"));
                user.put("email", rs.getString("email"));
                users.add(user);
            }
            
            request.setAttribute("users", users);
            request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
            
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp");
        }
    }
}