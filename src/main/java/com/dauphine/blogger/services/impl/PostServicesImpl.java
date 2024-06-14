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

/**
 * Implementation of PostServices to manage blog post operations.
 */
@Service
public class PostServicesImpl implements PostServices {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Constructs a PostServicesImpl with necessary repositories.
     *
     * @param postRepository Repository for post operations
     * @param categoryRepository Repository for category operations
     */
    public PostServicesImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all posts from the database.
     *
     * @return a list of all posts
     */
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * Fetches a single post by its UUID.
     *
     * @param id the UUID of the post to find
     * @return the found post
     * @throws PostNotFoundByIdException if the post with the specified ID does not exist
     */
    @Override
    public Post getPostById(UUID id) throws PostNotFoundByIdException {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundByIdException(id));
    }

    @Override
    public Post postPost(String title, String content) {
        return null;
    }

    /**
     * Creates a new post with the specified title and content, and assigns it to an existing category.
     *
     * @param title the title of the new post
     * @param content the content of the new post
     * @param categoryUuid the UUID of the category to assign to the post
     * @return the newly created post
     * @throws CategoryNotFoundByIdException if the category with the specified UUID does not exist
     */
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
        return postRepository.save(newPost);
    }

    /**
     * Updates an existing post with new title and content.
     *
     * @param id the UUID of the post to update
     * @param title the new title for the post
     * @param content the new content for the post
     * @return the updated post
     * @throws PostNotFoundByIdException if the post with the specified ID does not exist
     */
    @Override
    public Post putPost(UUID id, String title, String content) throws PostNotFoundByIdException {
        Post post = getPostById(id);
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    /**
     * Deletes a post by its UUID.
     *
     * @param id the UUID of the post to delete
     */
    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }

    /**
     * Retrieves all posts that match the specified title pattern.
     *
     * @param title the title pattern to search for
     * @return a list of posts matching the title pattern
     */
    @Override
    public List<Post> getAllLikeNames(String title) {
        return postRepository.findAllLikeNames(title);
    }
}
