package com.backend.ecommerce.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "order_date", nullable = false)
        private LocalDate orderDate;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OrderItem> orderItems = new ArrayList<>();

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @ManyToOne
    @JoinColumn(name = "address_id")
        private Address address;

    public PaymentMethod getPayment() {
        return payment;
    }

    private PaymentMethod payment = PaymentMethod.CASH_ON_DELIVERY ;


    private Double TotalAmount;

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount() {
        Double totalAmount = (double) 0;
        for (OrderItem item : this.orderItems) {
            totalAmount+=item.getOrderedProductPrice();
        }
        this.TotalAmount =totalAmount ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}



