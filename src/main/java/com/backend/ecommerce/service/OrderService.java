package com.backend.ecommerce.service;


import com.backend.ecommerce.model.*;
import com.backend.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Order placeOrder(Long cartId, Address address) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (!optionalCart.isPresent()) {
            throw new RuntimeException("Cart Not Found");
        }

        Cart cart = optionalCart.get();
        List<CartItem> cartItems = cart.getItems();

        Address savedAddress = addressRepository.save(address);

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDate.now());
        order.setPayment(PaymentMethod.CASH_ON_DELIVERY);
        order.setAddress(savedAddress);
        orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItem.setOrderedProductPrice(orderItem.getProduct());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(order.calculateTotalAmount());

        orderRepository.save(order);

        // Remove cart items and update the cart

        cart.clearItems();
        cartRepository.save(cart);

        return order;
    }

    public List<Order> getAllOrders () {
            return orderRepository.findAll();
        }

        public void deleteOrderById (Long id){
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));

            orderItemRepository.deleteAll(order.getOrderItems());
            orderRepository.delete(order);
        }
        public void processCheckout(Integer userId, Long cartId, Address address) {
        Order order = this.placeOrder(cartId, address);

        Optional<User> savedUser = userRepository.findById(userId);

        if(savedUser.isPresent()){
            emailService.sendConfirmationEmail(order.getId(), savedUser.get().getEmail());
        }
        else{
            throw new RuntimeException("No Valid User");
        }



    }

}


