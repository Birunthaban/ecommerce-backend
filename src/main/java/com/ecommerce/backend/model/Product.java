package com.ecommerce.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Product_Table")
public class Product {
    @Id
    @Column(name="Product_Id",nullable = false)
    private int productId;

    @Column(name="Product_Name",nullable = false)
    private String productName;
    @Column(name="Product_Qty",nullable = false)
    private int productQut;
    @Column(name="Product_Price")
    private float productPrice;
    @Column(name="Product_Description")
    private String ProductDescription;
    @Column(name="made_In")
    private String madeInCountry;


}
