package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.service.ProductService;
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

        @GetMapping
        public ResponseEntity<List<Product>> getAllProducts() {
            List<Product> products = productService.showAllProducts();
            return ResponseEntity.ok(products);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
            Product product = productService.getProduct(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product);
        }

        @PostMapping
        public ResponseEntity<Product> addProduct(@RequestBody Product product) {
            productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
            boolean deleted = productService.deleteProduct(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }
        @GetMapping("/search")
        public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String name) {
            List<Product> productList = productService.searchProducts(name);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }



