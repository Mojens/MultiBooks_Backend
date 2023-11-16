package com.monero.multibooks.MultiBooks.Service.Auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class AuthService {


    @Value("${app.smtpHost}")
    private String host;

    @Value("${app.smtpUsername}")
    private String username;

    @Value("${app.smtpPassword}")
    private String password;


    public AuthService() {
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken){
        String smtpHost = host;
        String smtpUsername = username;
        String smtpPassword = password;
        int smtpPort = 587;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("your-email@example.com"));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setSubject("Password Reset Request");
            message.setText("Please click the following link to reset your password: "
                    + "http://yourwebsite.com/reset-password?token=" + resetToken);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email");
        }
    }


    }

