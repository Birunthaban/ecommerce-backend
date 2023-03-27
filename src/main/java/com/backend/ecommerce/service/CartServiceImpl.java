package com.backend.ecommerce.service;

import com.backend.ecommerce.model.Cart;
import com.backend.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private com.backend.ecommerce.repository.CartRepository cartRepository;

    @Override
    public List<Cart> showCart (Integer id){ return CartRepository.showcart(id);}

    @Override
    public String addToCart(Integer product_id, Integer units, Integer id){
        if(cartRepository.getExistingUnits(product_id,id)==null){
            cartRepository.addToCart(product_id,units,id);
        }else {
            Integer Units=cartRepository.getExistingUnits(product_id,id);
            cartRepository.updateCart(product_id,units+Units,id);
        }
        return "Product  added to cart ";
    }

    @Override
    public List<Cart> removeFromCart(Integer product_id, Integer id) {
        cartRepository.removeFromcart(product_id,id);
        return CartRepository.showcart(id);
    }


}
