package com.backend.ecommerce.repository;

import com.backend.ecommerce.model.Cart;
import com.backend.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
