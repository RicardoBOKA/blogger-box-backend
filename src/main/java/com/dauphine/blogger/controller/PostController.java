package com.dauphine.blogger.controller;

import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controller class to handle API requests related to blog posts.
 * Provides endpoints for CRUD operations on posts.
 */
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostServices postServices;

    /**
     * Constructor to initialize the PostController with necessary services.
     * @param postServices The service object for handling business logic related to posts.
     */
    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }

    /**
     * Endpoint to retrieve all posts or search posts by title if a 'name' parameter is provided.
     *
     * @param name Optional; filter posts by title if provided.
     * @return ResponseEntity with a list of posts; returns all posts if 'name' is not provided.
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAll(@RequestParam(required = false) String name) {
        List<Post> posts = name == null || name.isBlank() ?
                postServices.getAllPosts()
                : postServices.getAllLikeNames(name);
        return ResponseEntity.ok(posts);
    }

    /**
     * Endpoint to retrieve a single post by its UUID.
     *
     * @param id The UUID of the post to retrieve.
     * @return ResponseEntity containing the post if found; returns NOT_FOUND if the post does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {
        try {
            Post post = postServices.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundByIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Endpoint to create a new post with the provided title and content.
     *
     * @param postRequest Request body containing title, content, and categoryId.
     * @return ResponseEntity with the created post and its URI; returns NOT_FOUND if the specified category does not exist.
     */
    @PostMapping
    public ResponseEntity<Post> postPost(@RequestBody PostRequest postRequest) {
        UUID categoryUuid = postRequest.getCategoryId();
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

    /**
     * Endpoint to update an existing post identified by UUID with new title and content.
     *
     * @param id UUID of the post to update.
     * @param updatedPost Request body containing new title and content.
     * @return ResponseEntity with the updated post; returns NOT_FOUND if the post does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> putPost(@PathVariable UUID id, @RequestBody PostRequest updatedPost) throws PostNotFoundByIdException {
        Post updated = postServices.putPost(id, updatedPost.getTitle(), updatedPost.getContent());
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete a post by its UUID.
     *
     * @param id UUID of the post to delete.
     */
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postServices.deletePost(id);
    }
}
