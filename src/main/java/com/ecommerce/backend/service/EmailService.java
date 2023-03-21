package com.ecommerce.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    public String generateOtp() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Welcome to Oxygen Sports!");

        String message = "Dear valued customer,\n\n" +
                "Thank you for signing up with Oxygen Sports! We are excited to have you on board and look forward to helping you achieve your fitness goals.\n\n" +
                "Your One-Time Password (OTP) for registration is: " + otp + "\n\n" +
                "If you have any questions or concerns, please don't hesitate to contact us at support@oxygensports.com.\n\n" +
                "Best regards,\n" +
                "The Oxygen Sports Team";
        msg.setText(message);







        javaMailSender.send(msg);
    }


}

