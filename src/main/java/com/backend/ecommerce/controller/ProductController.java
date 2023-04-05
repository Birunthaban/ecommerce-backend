package com.backend.ecommerce.controller;

import com.backend.ecommerce.exception.ProductNotFoundException;
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

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get")
    public ResponseEntity<Product> getProductById(@RequestParam Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product , @RequestParam long categoryId) {
        productService.addProduct(product,categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id) {
        boolean deleted = productService.deleteProductById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        List<Product> productList = null;
        try {
            productList = productService.searchProductsByName(name);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/search_by_category")
    public ResponseEntity<List<Product>> searchProductsByCategory(@RequestParam long id) {
        List<Product> productList = null;

            productList = productService.searchProductsByCategoryId(id);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
