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

    public void sendVerificationEmail(String email, String verificationToken) throws MessagingException {
        String subject = "User Confirmation";
        String verificationLink = appUrl + "/auth/verify?token=" + verificationToken;
        String body = "Please click on this link to verify your email address: " + verificationLink;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText("<html>\n" +
                "<head>\n" +
                "	<title>UserEmail Confirmation</title>\n" +
                "	<style>\n" +
                "		.link-container {\n" +
                "			border: 1px solid #ccc;\n" +
                "			padding: 10px;\n" +
                "			display: inline-block;\n" +
                "			margin: 10px 0;\n" +
                "		}\n" +
                "		.link-container a {\n" +
                "			color: #333;\n" +
                "			text-decoration: none;\n" +
                "		}\n" +
                "	</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "	<h2>Thank you for registering with our service!</h2>\n" +
                "	<p>Please click on the link below to confirm your email address:</p>\n" +
                "	<div class=\"link-container\">\n" +
                "		<a href=\"" + verificationLink + "\">" + "Please click on this link to verify your account " + "</a>\n" +
                "	</div>\n" +
                "</body>\n" +
                "</html>",true);

        javaMailSender.send(message);
    }

    public MimeMessage createConfirmationMessage(Long order_id, String email) throws MessagingException {
        Optional<Order> existingOrder = orderRepository.findById(order_id);
        if (!existingOrder.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + order_id);
        }
        String customerName = existingOrder.get().getUser().getFirstname();
        String contactEmail = "support@oxygen.sports.com";
        String companyName = "Oxygen Sports";
        String orderDetails = getProductDetails(order_id);

        String htmlMessage = "<html><body>"
                + "<div style=\"font-family: Arial, sans-serif; font-size: 14px;\">"
                + "<p>Dear " + customerName + ",</p>"
                + "<p>Thank you for placing your order with us. We appreciate your business.</p>"
                + "<p>We are pleased to inform you that your order has been received and is being processed. Please see the details below:</p>"
                + "<table style=\"border-collapse: collapse; width: 100%;\">"
                + "<thead>"
                + "<tr style=\"background-color: #ccc;\">"
                + "<th style=\"padding: 10px; text-align: left;\">Product Name</th>"
                + "<th style=\"padding: 10px; text-align: left;\">Price per Unit</th>"
                + "<th style=\"padding: 10px; text-align: left;\">Quantity</th>"
                + "<th style=\"padding: 10px; text-align: left;\">Total Price</th>"
                + "</tr>"
                + "</thead>"
                + "<tbody>"
                + orderDetails
                + "</tbody>"
                + "</table>"
                + "<p>If you have any questions or concerns, please don't hesitate to contact us at <a href=\"mailto:" + contactEmail + "\">" + contactEmail + "</a>.</p>"
                + "<p>Thank you again for your order, and we look forward to serving you in the future.</p>"
                + "<p>Best regards,</p>"
                + "<p>" + companyName + "</p>"
                + "</div>"
                + "</body></html>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Confirmation Email");
        helper.setText(htmlMessage, true);
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
        double totalPrice = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            double itemPrice = item.getProduct().getPrice() * item.getQuantity();
            sb.append("<tr>")
                    .append("<td>").append(item.getProduct().getName()).append("</td>")
                    .append("<td>").append("Rs : ").append(item.getProduct().getPrice()).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>").append("Rs :").append(itemPrice).append("</td>")
                    .append("</tr>");
            totalPrice += itemPrice;
        }

        // Append total price to the end of the table
        sb.append("<tr>")
                .append("<td colspan=\"3\"><b>Total Price</b></td>")
                .append("<td><b>Rs :").append(totalPrice).append("</b></td>")
                .append("</tr>");

        return sb.toString();
    }



}
