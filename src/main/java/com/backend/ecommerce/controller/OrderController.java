package com.backend.ecommerce.controller;

import com.backend.ecommerce.model.Order;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService ;
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @PostMapping("/save")
    public ResponseEntity<Order> addProduct(@RequestBody Order order) {
        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder();
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
