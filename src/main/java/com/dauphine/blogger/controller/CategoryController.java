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
    public ResponseEntity<List<Category>> getAll(@RequestParam(required = false) String name) {
        List<Category> categories = name == null || name.isBlank() ?
                categoryServices.getAll()
                : categoryServices.getAllLikeNames(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/name")
    public ResponseEntity<Category> getCategoryByName(@RequestParam String name) {
        Optional<Category> cat = categoryServices.findByNameIgnoreCase(name);
        return cat.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        try {
            Category cat = categoryServices.getById(id);
            return ResponseEntity.ok(cat);
        } catch (CategoryNotFoundByIdException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest createdCategory) throws CategoryNameAlreadyExistsException{
        try {
            Category category = categoryServices.createCategory(createdCategory.getName());
            return ResponseEntity
                    .created(URI.create("v1/categories/" + category.getUuid()))
                    .body(category);

        } catch (CategoryNameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Category());
        }

        //Category category = categoryServices.createCategory(createdCategory.getName());
        //return ResponseEntity
        //        .created(URI.create("v1/categories/" + category.getUuid()))
        //        .body(category);
        //return categoryServices.createCategory(createdCategory.getName());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id,
                                   @RequestBody CategoryRequest updatedCategory) throws CategoryNotFoundByIdException {

        return ResponseEntity.ok(categoryServices.updateCategory(id, updatedCategory.getName()));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryServices.deleteCategory(id);
    }
}
