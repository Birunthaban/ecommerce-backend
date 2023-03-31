package com.backend.ecommerce.service;

import com.backend.ecommerce.exception.ProductNotFoundException;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();

    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product ) {
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
            throw new ProductNotFoundException("No products found for this query ");
        }
        return products;
    }

    public List<Product> searchProductsByCategory(String categoryName) throws ProductNotFoundException {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found for this category");
        }
        return products;
    }


}
