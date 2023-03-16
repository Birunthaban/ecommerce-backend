package com.ecommerce.backend.model;

import javax.persistence.*;


@Entity
@Table(name="Cart")
public class Cart {
    @Id
    @Column(name="Cart_Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="Product_Name",nullable = false)
    private String productName;
    @Column(name ="qty",nullable = false )
    private int qty;

    @Column(name="Unit_Price", nullable = false)
    private float unitPrice;



    public Cart(int id, String productName, int qty, float unitPrice) {
        this.id = id;
        this.productName = productName;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
}

