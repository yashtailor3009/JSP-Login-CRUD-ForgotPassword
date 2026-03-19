<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="com.db.DBConnection"%>
<%
    // Check if user is logged in
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
    <title>My Profile</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
    <style>
        .profile-box {
            width: 450px;
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 15px 40px rgba(0,0,0,0.25);
        }
        .profile-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .profile-header h2 {
            color: #333;
        }
        .profile-detail {
            margin: 20px 0;
            padding: 15px;
            background: #f9f9f9;
            border-radius: 10px;
        }
        .detail-row {
            display: flex;
            margin: 10px 0;
            padding: 8px;
            border-bottom: 1px solid #eee;
        }
        .detail-label {
            width: 100px;
            font-weight: bold;
            color: #555;
        }
        .detail-value {
            flex: 1;
            color: #333;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
            margin-top: 25px;
        }
        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            text-decoration: none;
            text-align: center;
        }
        .btn-edit {
            background: linear-gradient(to right, #ff105f, #ffad06);
            color: white;
        }
        .btn-delete {
            background: #dc3545;
            color: white;
        }
        .btn-logout {
            background: #6c757d;
            color: white;
        }
        .back-link {
            display: block;
            margin-top: 20px;
            text-align: center;
            color: #666;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="hero">
        <div class="profile-box">
            <div class="profile-header">
                <h2>👤 My Profile</h2>
            </div>
            
            <div class="profile-detail">
                <div class="detail-row">
                    <span class="detail-label">User ID:</span>
                    <span class="detail-value"><%= userId %></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Username:</span>
                    <span class="detail-value"><%= username %></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Email:</span>
                    <span class="detail-value"><%= email %></span>
                </div>
            </div>
            
            <div class="action-buttons">
                <a href="edit-profile.jsp" class="btn btn-edit">✏️ Edit Profile</a>
                <a href="delete-account.jsp" class="btn btn-delete">🗑️ Delete Account</a>
            </div>
            
            <a href="logout" class="back-link">← Logout</a>
        </div>
    </div>
</body>
</html>