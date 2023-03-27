package com.backend.ecommerce.dto;

import javax.persistence.Column;

public class CartDto {
    private Integer cart_id;

    public Integer id;
    public Integer product_id;
    private Integer quantity;
    public Integer getCart_id() {return cart_id;}

    public void setCart_id(Integer cart_id) {this.cart_id = cart_id;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Integer getProduct_id() {return product_id;}

    public void setProduct_id(Integer product_id) {this.product_id = product_id;}

    public Integer getQuantity() {return quantity;}

    public void setQuantity(Integer quantity) {this.quantity = quantity;}

    public CartDto(Integer cart_id, Integer id, Integer product_id, Integer quantity) {
        this.cart_id = cart_id;
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
    }
}



