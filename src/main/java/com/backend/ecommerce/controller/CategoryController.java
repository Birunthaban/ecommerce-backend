package com.backend.ecommerce.controller;


import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> products = categoryService.getAllProductsCategories();
        return ResponseEntity.ok(products);
    }
    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }



}
