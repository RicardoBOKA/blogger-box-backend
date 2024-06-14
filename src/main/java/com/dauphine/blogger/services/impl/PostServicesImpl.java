package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repository.CategoryRepository;
import com.dauphine.blogger.repository.PostRepository;
import com.dauphine.blogger.services.PostServices;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostServicesImpl implements PostServices {

    private final PostRepository repository;
    private final CategoryRepository categoryRepository;


    public PostServicesImpl(PostRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Post> getAllPosts() {
        return repository.findAll();
    }

    @Override
    public Post getPostById(UUID id) throws PostNotFoundByIdException {
        return repository.findById(id)
                .orElseThrow(() -> new PostNotFoundByIdException(id));
    }

    @Override
    public Post postPost(String title, String content) {
        return null;
    }

    @Override
    public Post postPost(String title, String content, UUID categoryUuid) throws CategoryNotFoundByIdException {
        if (categoryUuid == null) {
            throw new IllegalArgumentException("Category UUID must not be null");
        }
        Category category = categoryRepository.findById(categoryUuid)
                .orElseThrow(() -> new CategoryNotFoundByIdException(categoryUuid));

        Post newPost = new Post();
        newPost.setUuid(UUID.randomUUID());
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setCategory(category);
        newPost.setCreatedDate(LocalDateTime.now());
        repository.save(newPost);
        return newPost;
    }

    @Override
    public Post putPost(UUID id, String title, String content) throws PostNotFoundByIdException {
        Post post = repository.findById(id)
                .orElseThrow(() -> new PostNotFoundByIdException(id));
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            repository.save(post);
        }
        //post.setTitle(title);
        //post.setContent(content);
        //repository.save(post);

        return post;
    }

    @Override
    public void deletePost(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Post> getAllLikeNames(String title) {
        return repository.findAllLikeNames(title);
    }
}
