package com.servlet;

import com.db.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email    = request.getParameter("email");
        String password = request.getParameter("password");
        String terms    = request.getParameter("terms");
        
        // Debug prints
        System.out.println("========== REGISTRATION ATTEMPT ==========");
        System.out.println("📝 Username: '" + username + "'");
        System.out.println("📧 Email: '" + email + "'");
        System.out.println("🔑 Password: '" + (password != null ? "********" : "null") + "'");
        System.out.println("✅ Terms accepted: '" + terms + "'");
        System.out.println("==========================================");
        
        String hashedPassword = null;
        if(password != null && !password.trim().isEmpty()) {
            hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        }

        boolean hasError = false;

        // ---------- BASIC VALIDATION ----------
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Username required");
            hasError = true;
        }

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email required");
            hasError = true;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password required");
            hasError = true;
        }

        if (terms == null) {
            request.setAttribute("termsError", "You must accept terms");
            hasError = true;
        }

        if (hasError) {
            RequestDispatcher rd =
                    request.getRequestDispatcher("/login.jsp?form=register");
            rd.forward(request, response);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            
            // ---------- INSERT NEW USER ----------
            String insertSql = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";
            
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, hashedPassword);
                
                int rowsAffected = ps.executeUpdate();
                
                if(rowsAffected > 0) {
                    System.out.println("🎉 User registered successfully: " + username);
                    // ✅ SUCCESS - Redirect to LOGIN FORM
                    response.sendRedirect("login.jsp?success=registered");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            
            // 🔴 HANDLE UNIQUE CONSTRAINT VIOLATIONS
            if (e.getMessage().contains("Duplicate entry")) {
                
                if (e.getMessage().contains("for key 'name'")) {
                    // Username already exists
                    request.setAttribute("usernameError", "❌ Username already taken! Choose another.");
                    System.out.println("❌ Duplicate username: " + username);
                    
                } else if (e.getMessage().contains("for key 'email'")) {
                    // Email already exists
                    request.setAttribute("emailError", "❌ Email already registered! Please login.");
                    System.out.println("❌ Duplicate email: " + email);
                    
                } else {
                    // Other duplicate error
                    request.setAttribute("globalError", "User already exists!");
                }
                
            } else {
                // Other database errors
                request.setAttribute("globalError", "Database error. Please try again.");
            }
            
            // Send back to register form with errors
            RequestDispatcher rd =
                    request.getRequestDispatcher("/login.jsp?form=register");
            rd.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "Server error. Please try again.");
            RequestDispatcher rd =
                    request.getRequestDispatcher("/login.jsp?form=register");
            rd.forward(request, response);
        }
    }
}