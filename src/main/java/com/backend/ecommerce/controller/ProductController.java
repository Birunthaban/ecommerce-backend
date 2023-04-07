package com.backend.ecommerce.controller;


import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/get")
    public ResponseEntity<Product> getProduct(@RequestParam Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String query) {
        List<Product> products = productService.searchProductsByName(query);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/search/category")
    public ResponseEntity<List<Product>> searchProductsByCategory(@RequestParam Long categoryId) {
        List<Product> products = productService.searchProductsByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
}
