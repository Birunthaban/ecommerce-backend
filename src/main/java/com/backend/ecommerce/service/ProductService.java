package com.backend.ecommerce.service;


import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.repository.CategoryRepository;
import com.backend.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();

    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new IllegalArgumentException("Invalid category id: " + categoryId);
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    public boolean deleteProductById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<Product> searchProductsByName(String query) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found for this query ");
        }
        return products;
    }

    public List<Product> searchProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

}
