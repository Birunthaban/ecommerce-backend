package com.backend.ecommerce.repository;

import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
