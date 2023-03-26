package com.backend.ecommerce.controller;



import com.backend.ecommerce.email.EmailService;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService mailService;

   @GetMapping("/all")
    public List<User> getAllUser(){
       return  userService.getAllUser();
   }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        if (mailService.verifyOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomerById(@PathVariable Integer id) {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
  }


}