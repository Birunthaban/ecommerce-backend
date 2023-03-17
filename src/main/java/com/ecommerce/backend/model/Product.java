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
    @Column(name="Product_Price",nullable = false)
    private float productPrice;
    @Column(name="Product_Description",nullable = false)
    private String productDescription;
    @Column(name="Made_In",nullable = false)
    private String madeInCountry;
    @Column(name="Product_Category",nullable = false)
    private  String productCategory;

}
