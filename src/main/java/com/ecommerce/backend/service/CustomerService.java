package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Customer;
import com.ecommerce.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAll(){

        return customerRepository.findAll();
    }
}
