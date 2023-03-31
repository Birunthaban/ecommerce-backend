package com.backend.ecommerce.service;


import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService mailService;
    //    LoginRequest loginRequest;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}

