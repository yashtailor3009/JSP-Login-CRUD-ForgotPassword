
package com.servlet;

import com.db.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        
        String username = request.getParameter("UserName");
        String password = request.getParameter("password");
        
        System.out.println("UserName = " + username);
        System.out.println("password = " + password);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try{
            Connection con = DBConnection.getConnection();
            
            String sql = "SELECT * FROM users WHERE name=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1,username);
            ps.setString(2,password);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                HttpSession session = request.getSession();
                session.setAttribute("username",username);
                
                response.sendRedirect("dashboard.jsp");
            }else {
                request.setAttribute("globalError", "Please fix the errors below");

                request.setAttribute("usernameError", "Invalid username");
                request.setAttribute("passwordError", "Wrong password");

                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.forward(request, response);
                }
        }catch (Exception e){
        e.printStackTrace();
        out.println("<h3>Error: " +e.getMessage()+ "</h3>");
        }
    }
}
