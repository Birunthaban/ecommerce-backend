package com.backend.ecommerce.repository;

import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String query);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.name = :categoryName")
    List<Product> findByCategoryName(String categoryName);




}
