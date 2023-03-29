package com.backend.ecommerce.controller;


import com.backend.ecommerce.model.Cart;
import com.backend.ecommerce.model.CartItem;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.repository.CartItemRepository;
import com.backend.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestParam Integer user_id) {
        Cart cart = cartService.createCart(user_id);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        List<CartItem> cartItems = cartService.getCartItems(cart);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<String> addCartItem(@PathVariable Long cartId, @RequestParam int user_id, @RequestBody Product product, @RequestParam int quantity) {
        cartService.addCartItem(cartId, user_id, product, quantity);
        return ResponseEntity.ok("Item added to cart");
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        Optional<CartItem> cartItem = cart.getItems().stream().filter(item -> item.getId().equals(itemId)).findFirst();
        if (cartItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cartService.removeCartItem(cart, cartItem.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> clearCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        cartService.clearCart(cart);
        return ResponseEntity.ok().build();
    }
}
