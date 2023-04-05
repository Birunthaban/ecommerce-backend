package com.backend.ecommerce.service;

import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> getAllProductsCategories() {

        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);

    }

    public void deleteCategoryById(Long categoryId) {

            categoryRepository.deleteById(categoryId);

    }
}
