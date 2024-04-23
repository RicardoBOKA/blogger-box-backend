package com.dauphine.blogger.controller;


import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
public class PostController {
    private final List<Post> temporaryPosts;

    public PostController() {
        temporaryPosts = new ArrayList<>();
        Category sampleCategory = new Category(UUID.randomUUID(), "Sample Category");
        temporaryPosts.add(new Post(UUID.randomUUID(), "Titre post n°1", "Contenu du post n°1", LocalDateTime.now(), sampleCategory));
        temporaryPosts.add(new Post(UUID.randomUUID(), "Titre post n°2", "Contenu du post n°2", LocalDateTime.now(), sampleCategory));
        temporaryPosts.add(new Post(UUID.randomUUID(), "Titre post n°3", "Contenu du post n°3", LocalDateTime.now(), sampleCategory));
    }

    /**
     * Récupère tous les posts disponibles.
     * @return une liste de tous les posts.
     */
    @GetMapping
    public List<Post> getAllPosts() {
        return temporaryPosts;
    }

    /**
     * Récupère un posts par son ID.
     * @return un post.
     */
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        Optional<Post> post = temporaryPosts.stream().filter(c -> c.getUuid().equals(id)).findFirst();
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()).getBody();
    }

    /**
     * Crée un nouveau post à partir des données fournies et l'ajoute à la liste des posts.
     * @param post le post à ajouter, fourni dans le corps de la requête.
     * @return une ResponseEntity contenant le post créé avec un statut HTTP 201.
     */

    @PostMapping
    public ResponseEntity<Post> postPost(@RequestBody Post post) {
        post.setUuid(UUID.randomUUID());
        post.setCrated_date(LocalDateTime.now());
        temporaryPosts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Post> putPost(@PathVariable UUID id, @RequestBody Post updatedPost) {
        Optional<Post> postOptional = temporaryPosts.stream().filter(p -> p.getUuid().equals(id)).findFirst();

        if (postOptional.isPresent()) {
            System.out.println("TEST");
            Post post = postOptional.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        boolean isRemoved = temporaryPosts.removeIf(p -> p.getUuid().equals(id));
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
