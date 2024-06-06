package com.dauphine.blogger.controller;


import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<Post>> getAll(@RequestParam(required = false) String name) {
        List<Post> posts = name == null || name.isBlank() ?
                postServices.getAllPosts()
                : postServices.getAllLikeNames(name);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {
        try {
            Post post = postServices.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundByIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Post> postPost(@RequestBody PostRequest postRequest) {
        UUID categoryUuid = postRequest.getCategoryId();
        System.out.println("UUUUUuuuuIIIIDDDDD2 = "+ categoryUuid);
        if (categoryUuid == null) {
            return ResponseEntity.badRequest().body(new Post());
        }
        try {
            Post post = postServices.postPost(postRequest.getTitle(), postRequest.getContent(), postRequest.getCategoryId());
            return ResponseEntity
                    .created(URI.create("v1/posts/" + post.getUuid()))
                    .body(post);
        } catch (CategoryNotFoundByIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Post> putPost(@PathVariable UUID id, @RequestBody PostRequest updatedPost) throws PostNotFoundByIdException{
        return ResponseEntity.ok(postServices.putPost(id, updatedPost.getTitle(), updatedPost.getContent()));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postServices.deletePost(id);
    }


}
