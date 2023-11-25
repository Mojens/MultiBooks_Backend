package com.monero.multibooks.MultiBooks.Service.Auth;

import com.monero.multibooks.MultiBooks.Dto.Auth.ResetPasswordRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Entities.Auth.ResetToken;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.Auth.ResetTokenRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@Service
public class AuthService {


    @Value("${app.smtpHost}")
    private String host;

    @Value("${app.smtpUsername}")
    private String username;

    @Value("${app.smtpPassword}")
    private String password;

    private final ResetTokenRepository resetTokenRepository;
    private final UserRepository userRepository;


    public AuthService(ResetTokenRepository resetTokenRepository,
                       UserRepository userRepository) {
        this.resetTokenRepository = resetTokenRepository;
        this.userRepository = userRepository;
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
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

            message.setFrom(new InternetAddress("keamohammadmnodejs@gmail.com"));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setSubject("Password Reset Request");
            message.setText("Please click the following link to reset your password: "
                    + "http://localhost:4200/reset-password/" + resetToken);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email");
        }
    }

    public ApiResponse getResetToken(String email) {
        String resetToken;
        int maxAttempts = 10;
        int attempts = 0;
        do {
            resetToken = UUID.randomUUID().toString();
            Optional<ResetToken> existingToken = resetTokenRepository.findById(resetToken);

            if (existingToken.isEmpty()) {
                break;
            }
            attempts++;
        } while (attempts < maxAttempts);

        if (attempts >= maxAttempts) {
            throw new IllegalStateException("Unable to generate a unique reset token after maximum attempts.");
        }
        User user = userRepository.findById(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Instant now = Instant.now();
        Instant tokenExpiry = now.plus(24, ChronoUnit.HOURS);
        ResetToken newToken = ResetToken.builder()
                .token(resetToken)
                .tokenExpiry(tokenExpiry)
                .user(user)
                .build();
        resetTokenRepository.save(newToken);

        return new ApiResponse(resetToken, "Generated new token");
    }

    public ApiResponse resetPassword(@RequestBody ResetPasswordRequest request) {
        ResetToken foundResetToken = resetTokenRepository.findById(request.getResetToken()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found"));
        Instant now = Instant.now();

        if (now.isAfter(foundResetToken.getTokenExpiry())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token has expired");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        User user = foundResetToken.getUser();
        user.setPassword(request.getPassword());
        userRepository.save(user);

        resetTokenRepository.delete(foundResetToken);

        return new ApiResponse(null, "Password has been successfully reset");
    }

    public ApiResponse verifyToken(@PathVariable String token){
        ResetToken foundResetToken = resetTokenRepository.findById(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link is broken, please request a new one"));
        Instant now = Instant.now();

        if (now.isAfter(foundResetToken.getTokenExpiry())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Link has expired, please request a new one");
        }
        return new ApiResponse(null, "Token is valid");
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public void validateUserAccess(String mail) {
        User loggedInUser = getAuthenticatedUser();
        if (!loggedInUser.getUsername().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupExpiredTokens() {
        Instant now = Instant.now();
        resetTokenRepository.deleteAllByTokenExpiryBefore(now);
    }

}

