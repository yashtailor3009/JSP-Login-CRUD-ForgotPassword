<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
    <style>
        .forgot-box {
            width: 380px;
            min-height: 320px;
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 15px 40px rgba(0,0,0,0.25);
            text-align: center;
        }
        
        .forgot-box h2 {
            color: #333;
            margin-bottom: 15px;
        }
        
        .forgot-box p {
            color: #666;
            font-size: 14px;
            margin-bottom: 25px;
            line-height: 1.5;
        }
        
        .message {
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 13px;
        }
        
        .success {
            background: #e8f5e9;
            border: 1px solid #4caf50;
            color: #2e7d32;
        }
        
        .error {
            background: #ffebee;
            border: 1px solid #ff4d4d;
            color: #c00000;
        }
        
        .back-link {
            margin-top: 20px;
            display: inline-block;
            color: #666;
            text-decoration: none;
            font-size: 13px;
            transition: color 0.3s;
        }
        
        .back-link:hover {
            color: #ff105f;
        }
        
        .submit-btn {
            width: 100%;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="hero">
        <div class="forgot-box">
            <h2>🔐 Forgot Password?</h2>
            
            <p>Enter your email address and we'll send you a link to reset your password.</p>
            
            <!-- Success/Error Messages -->
            <% if(request.getParameter("success") != null) { %>
                <div class="message success">
                    ✅ Password reset link has been sent to your email!
                </div>
            <% } %>
            
            <% if(request.getParameter("error") != null) { 
                String error = request.getParameter("error");
                String errorMsg = "";
                if("notfound".equals(error)) errorMsg = "Email not found. Please try again.";
                else if("server".equals(error)) errorMsg = "Server error. Please try again later.";
                else errorMsg = "Something went wrong. Please try again.";
            %>
                <div class="message error">
                    ❌ <%= errorMsg %>
                </div>
            <% } %>
            
            <!-- Forgot Password Form -->
            <form action="forgot-password" method="post">
                <input type="email" 
                       name="email" 
                       class="input-field" 
                       placeholder="Enter your email" 
                       required 
                       style="margin-bottom: 15px;" />
                
                <button type="submit" class="submit-btn">Send Reset Link</button>
            </form>
            
            <a href="login.jsp" class="back-link">← Back to Login</a>
        </div>
    </div>
</body>
</html>