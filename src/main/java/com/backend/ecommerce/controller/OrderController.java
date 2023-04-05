package com.backend.ecommerce.controller;

import com.backend.ecommerce.model.*;
import com.backend.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> processCheckout(@RequestParam Integer userId,@RequestParam Long cartId, @RequestBody Address address) {
        orderService.checkout(userId,cartId, address);
     return ResponseEntity.ok("checkout sucessful");
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }


}
