package com.backend.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name="NewCart")

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cart_id",length = 5)
    public Integer cart_id;
    //userid -> id
    @Column(name="id",length = 5)
    public Integer id;
    @Column(name="product_id",length = 5)
    public Integer product_id;

    @Column(name="quantity",length = 5)
    private Integer quantity;

    public Cart(Integer cart_id, Integer id, Integer product_id, Integer quantity) {
        this.cart_id = cart_id;
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public Cart() {

    }

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
