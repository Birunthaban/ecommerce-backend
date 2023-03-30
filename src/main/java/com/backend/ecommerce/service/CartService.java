package com.backend.ecommerce.service;

import com.backend.ecommerce.exception.CartNotFoundException;
import com.backend.ecommerce.exception.UserNotFoundException;
import com.backend.ecommerce.model.Cart;
import com.backend.ecommerce.model.CartItem;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.CartRepository;
import com.backend.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart createCart(Integer userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        Cart cart = new Cart();
        cart.setUser(existingUser.orElseThrow(() -> new UserNotFoundException("User not found")));
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    public List<CartItem> getCartItems(Cart cart) {
        return cart.getItems();
    }

    public void addCartItem(long cartId, int userId, Product product, int quantity) {
        Cart actualCart = cartRepository.findById(cartId)
                .orElseGet(() -> createCart(userId));
        List<CartItem> cartItems = actualCart.getItems();
        boolean isItemFound = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                isItemFound = true;
                break;
            }
        }
        if (!isItemFound) {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(actualCart);
            cartItems.add(newItem);
        }
        cartRepository.save(actualCart);
    }

    public void removeCartItem(Cart cart, CartItem cartItem) {
        List<CartItem> cartItems = cart.getItems();
        cartItems.remove(cartItem);
        cartRepository.save(cart);
    }

    public void clearCart(Cart cart) {
        cart.getItems().clear();
        cartRepository.save(cart);
    }


}
