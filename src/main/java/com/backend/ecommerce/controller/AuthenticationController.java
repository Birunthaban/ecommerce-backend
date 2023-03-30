package com.backend.ecommerce.controller;


import com.backend.ecommerce.dto.Request.AuthenticationRequest;
import com.backend.ecommerce.dto.Request.RegisterRequest;
import com.backend.ecommerce.dto.Response.AuthenticationResponse;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;
    private final AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String verificationToken) {

        Optional<User> optionalUser = userRepository.findByLink(verificationToken);
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
