package com.dauphine.blogger.services;

import com.dauphine.blogger.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryServices {
    List<Category> getAll();
    Category getById(UUID id);
    Category createCategory(String name);
    Category updateCategory(UUID id, String name);
    void deleteCategory(UUID id);
}
