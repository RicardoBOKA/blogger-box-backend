package com.dauphine.blogger.controller;

import com.dauphine.blogger.models.Category;
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
    private final List<Category> temporaryCategories;

    public CategoryController() {
        temporaryCategories = new ArrayList<>();
        temporaryCategories.add(new Category(UUID.randomUUID(), "My first category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "My second category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "My third category"));
    }

    @GetMapping
    public List<Category> retrieveAllCategories() {
        return temporaryCategories;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        Optional<Category> category = temporaryCategories.stream().filter(c -> c.getUuid().equals(id)).findFirst();

        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        category.setUuid(UUID.randomUUID());
        temporaryCategories.add(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id,
                                                   @RequestBody Category updatedCategory) {
        Optional<Category> categoryOptional = temporaryCategories.stream().filter(c -> c.getUuid().equals(id)).findFirst();
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(updatedCategory.getName());
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        boolean isDeleted = temporaryCategories.removeIf(c -> c.getUuid().equals(id));

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
