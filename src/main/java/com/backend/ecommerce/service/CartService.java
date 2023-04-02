package com.backend.ecommerce.service;

import com.backend.ecommerce.exception.CartNotFoundException;
import com.backend.ecommerce.exception.UserNotFoundException;
import com.backend.ecommerce.model.Cart;
import com.backend.ecommerce.model.CartItem;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.repository.CartRepository;
import com.backend.ecommerce.repository.ProductRepository;
import com.backend.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

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

    public String addCartItem(long cartId, Long productId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalCart.isPresent() && optionalProduct.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> cartItems = cart.getItems();

            Optional<CartItem> optionalCartItem = cartItems.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartRepository.save(cart);
                return "Item quantity updated";
            } else {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(optionalProduct.get());
                newCartItem.setQuantity(quantity);
                newCartItem.setCart(cart);
                cartItems.add(newCartItem);
                cartRepository.save(cart);
                return "Item added to cart";
            }
        } else {
            return "Invalid cart or product ID";
        }
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
    public Cart createOrGetCart(int userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> createCart(userId));
    }

}
