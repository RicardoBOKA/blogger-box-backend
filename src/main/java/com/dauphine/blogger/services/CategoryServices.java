package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryServices {
    List<Category> getAll();
    Category getById(UUID id) throws CategoryNotFoundByIdException;
    Category createCategory(String name) throws CategoryNameAlreadyExistsException;
    Category updateCategory(UUID id, String name) throws CategoryNotFoundByIdException;
    void deleteCategory(UUID id);

    List<Category> getAllLikeNames(String name);
    Optional<Category> findByNameIgnoreCase(String name);

}
