package com.backend.ecommerce.service;

import com.backend.ecommerce.model.Order;
import com.backend.ecommerce.model.OrderItem;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.OrderRepository;
import com.backend.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private OrderRepository orderRepository;
    @Value("${app.url}")
    private String appUrl;

    public void sendVerificationEmail(String email, String verificationToken) {
        String subject = "Please verify your email address";
        String verificationLink = appUrl + "/auth/verify?token=" + verificationToken;
        String body = "Please click on this link to verify your email address: " + verificationLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    public MimeMessage createConfirmationMessage(Long order_id, String email) throws MessagingException {
        Optional<Order> existingOrder = orderRepository.findById(order_id);
        if (!existingOrder.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + order_id);
        }
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Confirmation Email");
        helper.setText("<html><body>"
                + "<h2>Thank you for placing your order with us. Your order has been received and is being processed.</h2>"
                + "<table border=\"2\">"
                + "<tr>"
                + "<th>Product Name</th>"
                + "<th>price of each product</th>"
                + "<th>Quantity</th>"
                + "<th>price of total</th>"
                + "</tr>"
                + getProductDetails(order_id)
                + "</table>"
                + "</body></html>", true);
        return message;
    }

    public void sendConfirmationEmail(Long orderId, String email) {
        try {
            // Create the email message
            MimeMessage message = createConfirmationMessage(orderId, email);

            // Send the email
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // If an exception occurs, wrap it in a runtime exception and re-throw it
            throw new RuntimeException("Failed to send confirmation email", e);
        }
    }

    private String getProductDetails(Long order_id) {
        Optional<Order> optionalOrder = orderRepository.findById(order_id);
        Order order = optionalOrder.orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + order_id));

        StringBuilder sb = new StringBuilder();
        for (OrderItem item : order.getOrderItems()) {
            sb.append("<tr>")
                    .append("<td>").append(item.getProduct().getName()).append("</td>")
                    .append("<td>").append("Rs : ").append(item.getProduct().getPrice()).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>").append("Rs :").append(item.getProduct().getPrice() * item.getQuantity()).append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }


}
