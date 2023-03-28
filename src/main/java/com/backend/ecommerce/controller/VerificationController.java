package com.backend.ecommerce.controller;

import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Optional;

public class VerificationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String verificationToken) {

        Optional<User> optionalUser = userRepository.findByVerificationToken(verificationToken);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid verification token");
        }

        User user = optionalUser.get();
        user.setStatus(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Your email has been verified. You can now log in.");
    }
}
