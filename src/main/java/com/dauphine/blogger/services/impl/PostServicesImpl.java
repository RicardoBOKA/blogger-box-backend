package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostServices;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServicesImpl implements PostServices {

    private final List<Post> temporaryPosts;

    public PostServicesImpl() {
        temporaryPosts = new ArrayList<>();
        Category sampleCategory = new Category(UUID.randomUUID(), "Sample Category");
        temporaryPosts.add(new Post(UUID.randomUUID(), "Titre post n°1", "Contenu du post n°1", LocalDateTime.now(), sampleCategory));
        temporaryPosts.add(new Post(UUID.randomUUID(), "Titre post n°2", "Contenu du post n°2", LocalDateTime.now(), sampleCategory));
        temporaryPosts.add(new Post(UUID.randomUUID(), "Titre post n°3", "Contenu du post n°3", LocalDateTime.now(), sampleCategory));
    }
    @Override
    public List<Post> getAllPosts() {
        return temporaryPosts;

    }

    @Override
    public Post getPostById(UUID id) {
        return temporaryPosts.stream().filter(c -> c.getUuid().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Post postPost(String title, String content) {
        Post newPost = new Post();
        newPost.setUuid(UUID.randomUUID());
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setCrated_date(LocalDateTime.now());
        temporaryPosts.add(newPost);
        return newPost;
    }

    @Override
    public Post putPost(UUID id, String title, String content) {
        Post post = temporaryPosts.stream().filter(c -> c.getUuid().equals(id)).findFirst().orElse(null);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
        }

        return post;
    }

    @Override
    public void deletePost(UUID id) {
        temporaryPosts.stream().filter(c -> c.getUuid().equals(id)).findFirst().orElse(null);
    }
}
