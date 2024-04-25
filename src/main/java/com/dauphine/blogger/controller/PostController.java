package com.dauphine.blogger.controller;


import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.services.PostServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    PostServices postServices;

    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }

    /**
     * Récupère tous les posts disponibles.
     * @return une liste de tous les posts.
     */
    @GetMapping
    public List<Post> getAllPosts() {
        return postServices.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return postServices.getPostById(id);
    }

    @PostMapping
    public Post postPost(@RequestBody PostRequest newPost) {
        return postServices.postPost(newPost.getTitle(), newPost.getContent());
    }


    @PutMapping("/{id}")
    public Post putPost(@PathVariable UUID id, @RequestBody PostRequest updatedPost) {
        return postServices.putPost(id, updatedPost.getTitle(), updatedPost.getContent());
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postServices.deletePost(id);
    }


}
