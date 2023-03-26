package com.backend.ecommerce.service;

import com.backend.ecommerce.model.Order;
import com.backend.ecommerce.model.OrderItem;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.OrderItemRepository;
import com.backend.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order saveOrder(Order order) {
        // Set the order date to the current date
        order.setOrderDate(LocalDate.now());

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Set the order on each order item and save them
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    public List<Order> getOrdersByUserId(Long Id){
        return orderRepository.findByUserId(Id);
    }
    public void deleteOrder(Order order) {
        // Delete all order items associated with the order
        orderItemRepository.deleteAll(order.getOrderItems());

        // Delete the order
        orderRepository.delete(order);
    }
}