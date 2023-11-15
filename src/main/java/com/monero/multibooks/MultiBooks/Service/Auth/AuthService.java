package com.monero.multibooks.MultiBooks.Service.Auth;

import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
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


    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress("your-email@example.com"));

            // Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // Set the email subject and content
            message.setSubject("Password Reset Request");
            message.setText("Please click the following link to reset your password: "
                    + "http://yourwebsite.com/reset-password?token=" + resetToken);

            // Send the email
            Transport.send(message);

        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email");
        }
    }


    }

