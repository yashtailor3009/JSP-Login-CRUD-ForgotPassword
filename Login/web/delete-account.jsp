<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String username = (String) session.getAttribute("username");
    if(username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Account</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
    <style>
        .delete-box {
            width: 380px;
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 15px 40px rgba(0,0,0,0.25);
            text-align: center;
        }
        .warning {
            background: #fff3cd;
            border: 1px solid #ffc107;
            color: #856404;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .error {
            color: #dc3545;
            font-size: 13px;
            margin: 5px 0;
        }
        .btn-delete {
            background: #dc3545;
            color: white;
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 30px;
            cursor: pointer;
            font-weight: 600;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="hero">
        <div class="delete-box">
            <h2 style="color:#dc3545;">⚠️ Delete Account</h2>
            
            <div class="warning">
                <strong>Warning!</strong><br>
                This action is permanent. All your data will be lost.
            </div>
            
            <% if(request.getParameter("error") != null) { 
                if(request.getParameter("error").equals("incorrect")) { %>
                    <div class="error">❌ Incorrect password!</div>
            <%  } else { %>
                    <div class="error">❌ Deletion failed. Try again.</div>
            <%  } 
               } %>
            
            <form action="delete-account" method="post">
                <input type="password" name="password" class="input-field" 
                       placeholder="Enter your password to confirm" required />
                
                <button type="submit" class="btn-delete">🗑️ Permanently Delete My Account</button>
            </form>
            
            <a href="profile.jsp" style="color:#666; text-decoration:none;">← Cancel</a>
        </div>
    </div>
</body>
</html>