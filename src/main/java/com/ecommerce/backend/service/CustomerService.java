package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Customer;
import com.ecommerce.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){

        return customerRepository.findAll();
    }
    public Customer addCustomer(Customer customer) {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encryptedPassword=passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encryptedPassword);
        return customerRepository.save(customer);
    }
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
