<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login and Registration Form</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<div class="hero">
    <div class="form-box">
        <div class="button-box">
            <div id="btn"></div>
            <button type="button" class="toggle-btn" onclick="showLogin()">Log In</button>
            <button type="button" class="toggle-btn" onclick="showRegister()">Register</button>
        </div>

        <!-- LOGIN FORM -->
        <form id="Login" class="input-group" action="login" method="post">
            <div class="social-icons" style="text-align: center; margin-bottom: 20px;">
                <img src="fb.png" width="32" height="32" style="margin: 0 8px;" alt="Facebook" />
                <img src="tw.png" width="32" height="32" style="margin: 0 8px;" alt="Twitter" />
                <img src="gp.png" width="32" height="32" style="margin: 0 8px;" alt="Google" />
            </div>

            <input type="text" name="UserName" class="input-field" placeholder="User Id" required />
            <input type="password" name="password" class="input-field" placeholder="Enter Password" required />

            <!-- SIMPLE FORGOT PASSWORD SECTION -->
            <div style="margin: 15px 0 20px 0; border: 1px solid #f0f0f0; border-radius: 12px; padding: 12px; background: #fafafa;">
                <div style="display: flex; align-items: center; gap: 8px; margin-bottom: 8px;">
                    <input type="checkbox" id="rememberMe" class="chech-box" style="width: 16px; height: 16px;" />
                    <label for="rememberMe" style="font-size: 13px; color: #555; cursor: pointer;">Remember Password</label>
                </div>
                
                <div style="height: 10px;"></div>
                
                <div style="text-align: center; font-size: 12px; color: #555;">
                    Need help? <a href="forgot-password.jsp" style="color: #ff105f; text-decoration: none; font-weight: 500;">Reset password</a>
                </div>
            </div>

            <button type="submit" class="submit-btn">Log In</button>
        </form>

        <!-- REGISTER FORM -->
        <form id="Register" class="input-group" action="register" method="post">
            <div class="social-icons" style="text-align: center; margin-bottom: 20px;">
                <img src="fb.png" width="32" height="32" style="margin: 0 8px;" alt="Facebook" />
                <img src="tw.png" width="32" height="32" style="margin: 0 8px;" alt="Twitter" />
                <img src="gp.png" width="32" height="32" style="margin: 0 8px;" alt="Google" />
            </div>

            <input type="text" name="username" class="input-field" placeholder="User Id" required />
            <input type="email" name="email" class="input-field" placeholder="Email Id" required />
            <input type="password" name="password" class="input-field" placeholder="Enter Password" required />

            <div class="remember-row">
                <input type="checkbox" name="terms" id="terms" class="chech-box" required />
                <label for="terms">I agree to the terms & conditions</label>
            </div>

            <button type="submit" class="submit-btn">Register</button>
        </form>
    </div>
</div>

<script>
    var x = document.getElementById("Login");
    var y = document.getElementById("Register");
    var z = document.getElementById("btn");

    function showLogin() {
        x.style.left = "45px";
        y.style.left = "450px";
        z.style.left = "0px";
    }

    function showRegister() {
        x.style.left = "-400px";
        y.style.left = "45px";
        z.style.left = "110px";
    }

    window.onload = showLogin;
</script>
</body>
</html>