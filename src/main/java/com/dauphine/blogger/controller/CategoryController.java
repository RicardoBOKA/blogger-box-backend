package com.dauphine.blogger.controller;

import com.dauphine.blogger.models.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    @PostMapping
    public String create(@RequestBody ElementRequest body){
        //TO DO Later, implement persistance layer
        //INSERT INTO .....()
        return "Create new element with title '%s and description '%s'"
                .formatted(body.getTitle(), body.getDescription());
    }

    @PutMapping("/elements/{id}/")
    public String updage(@PathVariable  Integer id,
                         @RequestBody ElementRequest body){
        //TO DO Later, implement persistance layer
        //UPDATE .....()
        return "Update element with title '%s and description '%s'"
                .formatted(body.getTitle(), body.getDescription());
    }

    @PatchMapping("/elements/{id}/description")
    public String patch(@PathVariable  Integer id,
                         @RequestBody String description){
        //TO DO Later, implement persistance layer
        //PATHC .....()
        return "Patch element with title '%s and description '%s'".formatted(id, description);
    }

    @DeleteMapping("/elements/{id}")
    public String delete(@PathVariable Integer id) {
        //TO DO Later, implement persistance layer
        //DELETE .....()
        return "Patch element with title '%s and description '%s'".formatted(id);
    }
}
