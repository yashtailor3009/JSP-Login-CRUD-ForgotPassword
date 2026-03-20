# 🔐 JSP Login & Registration System

A complete Java web application with Login, Registration, CRUD operations, and Forgot Password functionality using email.

## 🚀 Features

- ✅ User Registration with BCrypt password hashing
- ✅ User Login with Session Management
- ✅ "Remember Password" checkbox
- ✅ Forgot Password with Email Reset Link
- ✅ Profile View (Read)
- ✅ Edit Profile (Update)
- ✅ Delete Account (Delete)
- ✅ Admin Panel to view all users
- ✅ Responsive UI with Social Icons

## 🛠️ Tech Stack

- **Frontend:** JSP, HTML5, CSS3
- **Backend:** Java Servlets
- **Database:** MySQL
- **Security:** BCrypt for password hashing
- **Email:** JavaMail API (Gmail SMTP)
- **Server:** Apache Tomcat 10
- **Build Tool:** Maven

## 📁 Project Structure
``` Login/
├── src/
│ └── com/
│ ├── servlet/ # All Servlets
│ ├── db/ # Database Connection
│ └── util/ # Email Utility
├── web/
│ ├── login.jsp
│ ├── register.jsp
│ ├── profile.jsp
│ ├── edit-profile.jsp
│ ├── delete-account.jsp
│ ├── forgot-password.jsp
│ ├── reset-password.jsp
│ ├── style.css
│ └── WEB-INF/
│ ├── web.xml
│ └── lib/ # All JAR files
└── pom.xml
```


## 🗄️ Database Setup

```sql
CREATE DATABASE user_curd_db;

USE user_curd_db;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE password_reset_tokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    token VARCHAR(255) UNIQUE,
    expiry_date DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```
⚙️ Configuration
1. Database Connection
Update DBConnection.java:
  private static final String PASSWORD = "your_mysql_password";

2. Email Configuration
Update EmailUtility.java:
  private static final String SMTP_USER = "your-email@gmail.com";
  private static final String SMTP_PASSWORD = "your-16-digit-app-password";

## 📧 How to Get Gmail App Password
Enable 2-Factor Authentication in Google Account

Go to Security → App Passwords

Select "Mail" and "Windows Computer"

Generate 16-digit password

Use in EmailUtility.java

## 🚦 How to Run
1. Clone the repository
git clone https://github.com/yashtailor3009/JSP-Login-CRUD-ForgotPassword.git

2. Import into NetBeans/Eclipse

3. Create MySQL database using above script

4. Update database credentials in DBConnection.java

5. Update email credentials in EmailUtility.java

6. Build project (Maven)

7. Deploy on Tomcat 10

8.  Access: http://localhost:8080/Login/

## 📸 Screenshots

Registration Page
<img width="960" height="444" alt="Registration_page" src="https://github.com/user-attachments/assets/5c5e1a6f-8c08-4f13-a8cf-90866ad65a4f" />

Login Page
<img width="960" height="445" alt="Login_page" src="https://github.com/user-attachments/assets/da6956b1-2481-480b-9c75-60404766d965" />

Forgot Password Page
<img width="960" height="444" alt="Forgot_Password_Page" src="https://github.com/user-attachments/assets/49702a54-829d-4dad-8e1c-7356bd6b8b10" />

## 👨‍💻 Author
**Yash Tailor**  
- GitHub: [@yashtailor3009](https://github.com/yashtailor3009)
- Project: [JSP-Login-CRUD-ForgotPassword](https://github.com/yashtailor3009/JSP-Login-CRUD-ForgotPassword)

## 📝 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## ✅ Features Completed

| Section | Description |
|---------|-------------|
| ✅ **Database Setup** | Complete SQL script |
| ✅ **Configuration** | DB and Email setup guide |
| ✅ **Gmail App Password** | Step-by-step guide |
| ✅ **How to Run** | Clone to deploy instructions |
| ✅ **Author** | Your name and profile |
| ✅ **Screenshots** | Placeholder for images |
