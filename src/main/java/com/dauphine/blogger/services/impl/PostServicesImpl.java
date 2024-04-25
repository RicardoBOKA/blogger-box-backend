package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repository.CategoryRepository;
import com.dauphine.blogger.repository.PostRepository;
import com.dauphine.blogger.services.PostServices;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServicesImpl implements PostServices {

    private final PostRepository repository;

    public PostServicesImpl(PostRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Post> getAllPosts() {
        return repository.findAll();

    }

    @Override
    public Post getPostById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Post postPost(String title, String content) {
        Post newPost = new Post();
        newPost.setUuid(UUID.randomUUID());
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setCreated_date(LocalDateTime.now());
        repository.save(newPost);
        return newPost;
    }

    @Override
    public Post putPost(UUID id, String title, String content) {
        Post post = repository.findById(id).orElse(null);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            repository.save(post);
        }
        return post;
    }

    @Override
    public void deletePost(UUID id) {
        repository.deleteById(id);
    }
}
