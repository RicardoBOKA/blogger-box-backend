package com.dauphine.blogger.services.impl;


import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServicesImp implements CategoryServices {
    private final List<Category> temporaryCategories;

    public CategoryServicesImp() {
        temporaryCategories = new ArrayList<>();
        temporaryCategories.add(new Category(UUID.randomUUID(), "My first category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "My second category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "My third category"));
    }

    @Override
    public List<Category> getAll() {
        return temporaryCategories;
    }

    @Override
    public Category getById(UUID id) {
        return temporaryCategories.stream().filter(c -> c.getUuid().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Category createCategory(String name) {
        Category category = new Category(UUID.randomUUID(), name);
        temporaryCategories.add(category);
        return category;
    }

    @Override
    public Category updateCategory(UUID id, String name) {
        Category category = temporaryCategories.stream().filter(c -> c.getUuid().equals(id)).findFirst().orElse(null);
        if (category != null) {
            category.setName(name);
        }
        return category;
    }

    @Override
    public void deleteCategory(UUID id) {
        temporaryCategories.removeIf(c -> c.getUuid().equals(id));
    }
}
