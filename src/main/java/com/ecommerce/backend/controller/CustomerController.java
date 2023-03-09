package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Customer;
import com.ecommerce.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/customer")
    public List<Customer> get_all(){
        return customerService.getAllCustomers();
    }
    @PostMapping("/customer")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        Customer savedUser = customerService.addCustomer(customer);
        return ResponseEntity.created(URI.create("/user/" + savedUser.getId()))
                .body(savedUser);
    }
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
