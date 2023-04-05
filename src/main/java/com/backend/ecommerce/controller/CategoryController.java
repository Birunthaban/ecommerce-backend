package com.backend.ecommerce.controller;


import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<Category>> getAllCatergories() {
        List<Category> products = categoryService.getAllProductsCategories();
        return ResponseEntity.ok(products);
    }
}
