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
    @Column(name="Age_Group",nullable = false)
    private String ageGroup;

    public Product(int productId, String productName, int productQut, float productPrice, String productDescription, String madeInCountry, String productCategory, String ageGroup) {
        this.productId = productId;
        this.productName = productName;
        this.productQut = productQut;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.madeInCountry = madeInCountry;
        this.productCategory = productCategory;
        this.ageGroup = ageGroup;
    }

    public Product() {

    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQut() {
        return productQut;
    }

    public void setProductQut(int productQut) {
        this.productQut = productQut;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getMadeInCountry() {
        return madeInCountry;
    }

    public void setMadeInCountry(String madeInCountry) {
        this.madeInCountry = madeInCountry;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }
}
