package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repository.CategoryRepository;
import com.dauphine.blogger.services.CategoryServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing categories.
 * This class implements the business logic for category operations defined by the CategoryServices interface.
 */
@Service
public class CategoryServicesImp implements CategoryServices {
    private final CategoryRepository repository;

    /**
     * Constructor to initialize the CategoryServicesImp with a CategoryRepository.
     * @param repository the category repository used for database operations
     */
    public CategoryServicesImp(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all categories from the repository.
     * @return a list of all categories
     */
    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    /**
     * Fetches a category by its UUID.
     * @param id the UUID of the category to retrieve
     * @return the found category
     * @throws CategoryNotFoundByIdException if no category is found with the provided ID
     */
    @Override
    public Category getById(UUID id) throws CategoryNotFoundByIdException {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundByIdException(id));
    }

    /**
     * Creates a new category with the given name after checking for duplicates.
     * @param name the name of the new category
     * @return the saved category entity
     * @throws CategoryNameAlreadyExistsException if a category with the given name already exists
     */
    @Override
    public Category createCategory(String name) throws CategoryNameAlreadyExistsException {
        if (!repository.findAllLikeNames(name).isEmpty()) {
            throw new CategoryNameAlreadyExistsException(name);
        };
        Category category = new Category(UUID.randomUUID(), name);
        return repository.save(category);
    }

    /**
     * Updates the name of an existing category.
     * @param id the UUID of the category to update
     * @param name the new name for the category
     * @return the updated category, or null if no category was found with the provided ID
     * @throws CategoryNotFoundByIdException if the category with the given id is not found
     */
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

    /**
     * Deletes a category by its UUID.
     * @param id the UUID of the category to delete
     */
    @Override
    public void deleteCategory(UUID id) {
        repository.deleteById(id);
    }

    /**
     * Retrieves all categories that have names like the specified name.
     * @param name the name to search for
     * @return a list of categories that match the search criteria
     */
    @Override
    public List<Category> getAllLikeNames(String name) {
        return repository.findAllLikeNames(name);
    }

    /**
     * Finds a category by its name, ignoring case.
     * @param name the name of the category to find
     * @return an Optional containing the found category if it exists
     */
    @Override
    public Optional<Category> findByNameIgnoreCase(String name) {
        return repository.findByNameIgnoreCase(name);
    }
}
