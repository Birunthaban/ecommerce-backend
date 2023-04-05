package com.backend.ecommerce.controller;

import com.backend.ecommerce.dto.Request.RegisterRequest;
import com.backend.ecommerce.dto.Response.AuthenticationResponse;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.service.AuthenticationService;
import com.backend.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private  UserService userService;
    @GetMapping("/get")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCustomerById(@RequestParam Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
