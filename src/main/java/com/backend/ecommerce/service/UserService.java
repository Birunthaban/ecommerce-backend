package com.backend.ecommerce.service;


import com.backend.ecommerce.email.EmailService;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService mailService;
//    LoginRequest loginRequest;
    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public List<User> getAllUser(){
        return userRepo.findAll();
    }
    public User addUser(User user) {

        String encryptedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepo.save(user);
    }
    public User register(User user) {
        String encryptedPassword=passwordEncoder.encode(user.getPassword());
        // Generate OTP
        String otp = mailService.generateOtp();
        user.setOtp(otp);
        user.setStatus(false);
        user.setPassword(encryptedPassword);

        // Save user
        User savedUser = userRepo.save(user);

        // Send OTP email
        mailService.sendOtpEmail(user.getEmail(), otp);

        return savedUser;
    }



//    public boolean authenticate(@RequestBody com.group02.mobileshopsystem.api.Payload.Request.AuthenticationRequest loginRequest) {
//        User user = userRepo.findByEmail(loginRequest.getEmail());
//        if (user != null && passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
//            return true;
//        }
//        return false;
//    }
    public void deleteUserById(Integer id) {
        userRepo.deleteById(id);
    }
}

