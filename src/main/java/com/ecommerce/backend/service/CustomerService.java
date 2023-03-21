package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.model.Customer;
import com.ecommerce.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerService {

    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAllCustomers(){

        return customerRepository.findAll();
    }
    public Customer addCustomer(Customer customer) {
        //Autowired not possible for local variables

        String encryptedPassword=passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encryptedPassword);
        return customerRepository.save(customer);
    }
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
    public boolean authenticate(@RequestBody LoginRequest loginRequest) {
        Customer customer = customerRepository.findByEmail(loginRequest.getEmail());
        if (customer != null && passwordEncoder.matches(loginRequest.getPassword(),customer.getPassword())) {
            return true;
        }
        return false;
    }
    public Customer register(Customer customer) {
        String encryptedPassword=passwordEncoder.encode(customer.getPassword());

        EmailService emailService=new EmailService();

        // Generate OTP
        String otp = emailService.generateOtp();
        customer.setOtp(otp);
        customer.setStatus(false);
        customer.setPassword(encryptedPassword);

        // Save user
        Customer savedUser = customerRepository.save(customer);

        // Send OTP email
        emailService.sendOtpEmail(customer.getEmail(), otp);

        return savedUser;
    }
    public boolean verifyOtp(String email, String otp) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return false;
        }

        if (customer.getOtp().equals(otp)) {
            customer.setStatus(true);
            customerRepository.save(customer);
            return true;
        }

        return false;
    }


}
