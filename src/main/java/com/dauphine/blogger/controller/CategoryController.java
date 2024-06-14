package com.dauphine.blogger.controller;

import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

/**
 * REST controller for managing categories.
 * Provides CRUD operations for categories in the system.
 */
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryServices categoryServices;

    /**
     * Constructor for CategoryController.
     *
     * @param categoryServices The service used to perform category operations.
     */
    public CategoryController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    /**
     * Retrieves all categories or searches categories by name if a name parameter is provided.
     *
     * @param name Optional; the name of the category to search for.
     * @return a {@link ResponseEntity} containing a list of {@link Category} objects.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAll(@RequestParam(required = false) String name) {
        List<Category> categories = name == null || name.isBlank() ?
                categoryServices.getAll()
                : categoryServices.getAllLikeNames(name);
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name The name of the category to retrieve.
     * @return a {@link ResponseEntity} with the found category or not found status if the category does not exist.
     */
    @GetMapping("/name")
    public ResponseEntity<Category> getCategoryByName(@RequestParam String name) {
        Optional<Category> cat = categoryServices.findByNameIgnoreCase(name);
        return cat.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a category by its unique ID.
     *
     * @param id The UUID of the category.
     * @return a {@link ResponseEntity} containing the category or a not found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        try {
            Category cat = categoryServices.getById(id);
            return ResponseEntity.ok(cat);
        } catch (CategoryNotFoundByIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Creates a new category.
     *
     * @param createdCategory The category request object containing the name.
     * @return a {@link ResponseEntity} with the created category and URI, or a conflict if the name already exists.
     * @throws CategoryNameAlreadyExistsException if the category name is already in use.
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest createdCategory) throws CategoryNameAlreadyExistsException {
        Category category = categoryServices.createCategory(createdCategory.getName());
        return ResponseEntity
                .created(URI.create("v1/categories/" + category.getUuid()))
                .body(category);
    }

    /**
     * Updates an existing category.
     *
     * @param id The UUID of the category to update.
     * @param updatedCategory The category request object containing the new name.
     * @return a {@link ResponseEntity} with the updated category.
     * @throws CategoryNotFoundByIdException if the category with the given id is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id,
                                                   @RequestBody CategoryRequest updatedCategory) throws CategoryNotFoundByIdException {
        Category updatedCat = categoryServices.updateCategory(id, updatedCategory.getName());
        return ResponseEntity.ok(updatedCat);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The UUID of the category to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryServices.deleteCategory(id);
    }
}
