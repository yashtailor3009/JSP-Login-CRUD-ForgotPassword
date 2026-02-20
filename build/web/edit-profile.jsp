<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="com.db.DBConnection"%>
<%
    String username = (String) session.getAttribute("username");
    Integer userId = (Integer) session.getAttribute("userId");
    
    if(username == null || userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String email = "";
    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT email FROM users WHERE id = ?");
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            email = rs.getString("email");
        }
        con.close();
    } catch(Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
    <style>
        .edit-box {
            width: 400px;
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 15px 40px rgba(0,0,0,0.25);
        }
        .error {
            color: #dc3545;
            font-size: 13px;
            margin: 5px 0;
        }
        .success {
            color: #28a745;
            font-size: 13px;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <div class="hero">
        <div class="edit-box">
            <h2 style="text-align:center; margin-bottom:25px;">✏️ Edit Profile</h2>
            
            <% if(request.getParameter("error") != null) { %>
                <div class="error"><%= request.getParameter("error") %></div>
            <% } %>
            
            <form action="edit-profile" method="post">
                <input type="text" name="username" class="input-field" 
                       value="<%= username %>" required />
                
                <input type="email" name="email" class="input-field" 
                       value="<%= email %>" required />
                
                <h4 style="margin:15px 0 5px;">Password Change (Optional)</h4>
                <input type="password" name="currentPassword" class="input-field" 
                       placeholder="Current Password" required />
                
                <input type="password" name="newPassword" class="input-field" 
                       placeholder="New Password (leave blank to keep same)" />
                
                <button type="submit" class="submit-btn">Update Profile</button>
            </form>
            
            <a href="profile.jsp" style="display:block; text-align:center; margin-top:15px; color:#666;">← Back to Profile</a>
        </div>
    </div>
</body>
</html>