package com.backend.ecommerce.service;

import com.backend.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

public class PasswordService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private  PasswordEncoder passwordEncoder;


    public String sendResetPasswordEmail(String email) throws MessagingException {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a unique token for the password reset link
        String resetToken = UUID.randomUUID().toString();
        user.setLink(resetToken);
        userRepository.save(user);

        // Send the password reset email to the user
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);

        return  "Password reset email has been sent";
    }


        public String resetPassword(String token,String newPassword) {
            var user = userRepository.findByLink(token)
                    .orElseThrow(() -> new RuntimeException("Invalid reset token"));

            // Set the new password for the user
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setLink(null); // clear the reset token
            userRepository.save(user);

            return "Password has been reset successfully";
        }

}
