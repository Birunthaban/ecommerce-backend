package com.backend.ecommerce.service;

import com.backend.ecommerce.model.Cart;

import java.util.List;

public interface CartService {
    List<Cart> showCart (Integer id);
    String addToCart(Integer product_id,Integer units,Integer id);
    List<Cart> removeFromCart(Integer product_id, Integer id);
}
