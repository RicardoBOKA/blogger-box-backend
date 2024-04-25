package com.dauphine.blogger.controller;

import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;


@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    CategoryServices categoryServices;

    public CategoryController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    @GetMapping
    public List<Category> retrieveAllCategories() {
        return categoryServices.getAll();
    }


    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable UUID id) {
        return categoryServices.getById(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody String name) {
        return categoryServices.createCategory(name);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable UUID id,
                                   @RequestBody CategoryRequest updatedCategory) {
        return categoryServices.updateCategory(id, updatedCategory.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryServices.deleteCategory(id);
    }
}
