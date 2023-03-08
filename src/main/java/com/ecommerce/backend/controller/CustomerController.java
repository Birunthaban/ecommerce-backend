package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Customer;
import com.ecommerce.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/get_user")
    public List<Customer> get_all(){
        return customerService.getAll();
    }
}
