package com.backend.ecommerce.service;

import com.backend.ecommerce.model.*;
import com.backend.ecommerce.repository.CartRepository;
import com.backend.ecommerce.repository.OrderItemRepository;
import com.backend.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    private EmailService emailService;
    private CartRepository cartRepository;

    @Transactional
    public Order placeOrder(User user, Cart cart, Address address) {


        List<CartItem> cartItems = cart.getItems();


        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);


        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItem.setOrderedProductPrice(cartItem.getProduct());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount();
        orderRepository.save(order);
        cart.getItems().removeAll(cartItems);
        cartRepository.save(cart);

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));

        orderItemRepository.deleteAll(order.getOrderItems());
        orderRepository.delete(order);
    }
}