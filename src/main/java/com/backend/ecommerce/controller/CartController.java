package com.backend.ecommerce.controller;


import com.backend.ecommerce.model.Cart;
import com.backend.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cart")

public class CartController {

    @Autowired
    public CartService cartService;

    @PostMapping(path="product")
    public List<Cart> showCart(@RequestParam Integer id)
    {
        return cartService.showCart(id);
    }
    @PostMapping(path="remove")
    public List<Cart> RemoveItemfromCart(@RequestParam Integer id,Integer product_id){
        return cartService.removeFromCart(product_id,id);
    }


}
