package com.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {
    
    
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "yashtailor78@gmail.com";      // ✅ Apna Gmail idhar
    private static final String SMTP_PASSWORD = "mpqm wtpt ievp kfwj";    // ✅ 16-digit App Password idhar
    
    public static void sendResetEmail(String recipientEmail, String resetLink) {
        
        try {
            // SMTP Properties setup
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            
            // Create session with authentication
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
                }
            });
            
            // Enable debugging (true se console me details aayegi)
            session.setDebug(true);
            
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, 
                    InternetAddress.parse(recipientEmail));
            message.setSubject("🔐 Password Reset Request - Login System");
            
            // HTML Email Content
            String htmlContent = getEmailTemplate(resetLink);
            
            message.setContent(htmlContent, "text/html; charset=utf-8");
            
            // Send email
            Transport.send(message);
            
            System.out.println("✅ Reset email sent successfully to: " + recipientEmail);
            System.out.println("🔗 Reset link: " + resetLink);
            
        } catch (MessagingException e) {
            System.err.println("❌ Email sending failed!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String getEmailTemplate(String resetLink) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; margin: 0; }"
                + ".container { max-width: 500px; margin: 0 auto; background: white; "
                + "border-radius: 10px; padding: 30px; box-shadow: 0 5px 20px rgba(0,0,0,0.1); }"
                + "h2 { color: #333; margin-top: 0; }"
                + "p { color: #666; line-height: 1.6; }"
                + ".button { display: inline-block; background: linear-gradient(to right, #ff105f, #ffad06); "
                + "color: white; text-decoration: none; padding: 12px 30px; border-radius: 25px; "
                + "margin: 20px 0; font-weight: bold; border: none; }"
                + ".button:hover { opacity: 0.9; transform: scale(1.05); }"
                + ".footer { margin-top: 30px; font-size: 12px; color: #999; border-top: 1px solid #eee; "
                + "padding-top: 20px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<h2>🔐 Password Reset Request</h2>"
                + "<p>We received a request to reset your password. Click the button below to create a new password:</p>"
                + "<div style='text-align: center;'>"
                + "<a href='" + resetLink + "' class='button'>Reset Password</a>"
                + "</div>"
                + "<p><small>⚠️ This link will expire in 1 hour</small></p>"
                + "<p>If you didn't request this, please ignore this email. Your password will remain unchanged.</p>"
                + "<div class='footer'>"
                + "<p>© 2026 Login System. All rights reserved.</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
    
    
    public static void main(String[] args) {
        System.out.println("📧 Testing Email Utility...");
        System.out.println("=================================");
        
        
        String testEmail = "test@gmail.com";  // ✅ Yeh change karo
        String testLink = "http://localhost:8080/Login/reset-password.jsp?token=123456789";
        
        sendResetEmail(testEmail, testLink);
        
        System.out.println("=================================");
    }
}