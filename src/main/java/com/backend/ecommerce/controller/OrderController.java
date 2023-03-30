package com.backend.ecommerce.controller;

import com.backend.ecommerce.model.*;
import com.backend.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class OrderController {
    private OrderService orderService ;
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody User user , @RequestBody Cart cart , @RequestBody Address address) {
        Order savedOrder = orderService.placeOrder(user,cart,address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }


}
