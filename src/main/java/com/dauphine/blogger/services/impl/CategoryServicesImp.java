package com.dauphine.blogger.services.impl;


import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repository.CategoryRepository;
import com.dauphine.blogger.services.CategoryServices;
import jakarta.persistence.EntityNotFoundException;
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
    public Category getById(UUID id) throws CategoryNotFoundByIdException {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundByIdException(id));
    }

    @Override
    public Category createCategory(String name) throws CategoryNameAlreadyExistsException {
        if (!repository.findAllLikeNames(name).isEmpty()) {
            throw new CategoryNameAlreadyExistsException(name);
        };
        Category category = new Category(UUID.randomUUID(), name);

        return repository.save(category);
        //temporaryCategories.add(category);
        //return category;
    }

    @Override
    public Category updateCategory(UUID id, String name) throws CategoryNotFoundByIdException {
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

    @Override
    public List<Category> getAllLikeNames(String name) {
        return repository.findAllLikeNames(name);
    }

    @Override
    public Optional<Category> findByNameIgnoreCase(String name) {
        return repository.findByNameIgnoreCase(name);
    }
}
