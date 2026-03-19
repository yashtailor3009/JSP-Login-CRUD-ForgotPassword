<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reset Password</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
    <style>
        .reset-box {
            width: 380px;
            min-height: 380px;
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 15px 40px rgba(0,0,0,0.25);
            text-align: center;
        }
        .reset-box h2 {
            color: #333;
            margin-bottom: 20px;
        }
        .message {
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 13px;
        }
        .error {
            background: #ffebee;
            border: 1px solid #ff4d4d;
            color: #c00000;
        }
        .input-field {
            margin-bottom: 15px;
        }
        .submit-btn {
            width: 100%;
            margin-top: 10px;
        }
        .back-link {
            margin-top: 20px;
            display: inline-block;
            color: #666;
            text-decoration: none;
            font-size: 13px;
        }
        .back-link:hover {
            color: #ff105f;
        }
    </style>
</head>
<body>
    <div class="hero">
        <div class="reset-box">
            <h2>🔐 Reset Password</h2>
            
            <% if(request.getParameter("error") != null) { %>
                <div class="message error">
                    ❌ <%= request.getParameter("error") %>
                </div>
            <% } %>
            
            <form action="reset-password" method="post">
                <input type="hidden" name="token" value="<%= request.getParameter("token") %>" />
                
                <input type="password" 
                       name="newPassword" 
                       class="input-field" 
                       placeholder="New Password" 
                       required />
                
                <input type="password" 
                       name="confirmPassword" 
                       class="input-field" 
                       placeholder="Confirm Password" 
                       required />
                
                <button type="submit" class="submit-btn">Reset Password</button>
            </form>
            
            <a href="login.jsp" class="back-link">← Back to Login</a>
        </div>
    </div>
</body>
</html>