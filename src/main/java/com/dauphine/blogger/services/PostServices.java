package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.dto.PostRequest;

import java.util.List;
import java.util.UUID;

public interface PostServices {
    List<Post> getAllPosts();
    Post getPostById(UUID id) throws PostNotFoundByIdException;
    Post postPost(String title, String content);

    Post postPost(String title, String content, UUID categoryUuid) throws CategoryNotFoundByIdException;

    Post putPost(UUID id, String title, String content) throws PostNotFoundByIdException;
    void deletePost(UUID id);
    List<Post> getAllLikeNames(String name);
}
