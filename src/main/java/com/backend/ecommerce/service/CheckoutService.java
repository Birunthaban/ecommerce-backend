package com.backend.ecommerce.service;

import com.backend.ecommerce.model.Address;
import com.backend.ecommerce.model.Cart;
import com.backend.ecommerce.model.Order;
import com.backend.ecommerce.model.User;

public class CheckoutService {
    private EmailService emailService;
    private OrderService orderService;

    public void processCheckout(User user, Cart cart, Address address) {
        Order order = orderService.placeOrder(user, cart, address);

        emailService.sendConfirmationEmail(order.getId(), user.getEmail());
    }

}
