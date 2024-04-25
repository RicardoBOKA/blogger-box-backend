package com.dauphine.blogger.services.impl;


import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repository.CategoryRepository;
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
    //private final List<Category> temporaryCategories;
    private final CategoryRepository repository;

    public CategoryServicesImp(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getById(UUID id) {
        return repository.findById(id).orElse(null);
        //return temporaryCategories.stream().filter(c -> c.getUuid().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Category createCategory(String name) {
        Category category = new Category(UUID.randomUUID(), name);
        return repository.save(category);
        //temporaryCategories.add(category);
        //return category;
    }

    @Override
    public Category updateCategory(UUID id, String name) {
        Category category = getById(id);
        if (category != null) {
            category.setName(name);
            return repository.save(category);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCategory(UUID id) {
        repository.deleteById(id);
    }
}
