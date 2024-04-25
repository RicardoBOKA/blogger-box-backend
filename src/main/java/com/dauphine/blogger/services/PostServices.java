package com.dauphine.blogger.services;

import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.dto.PostRequest;

import java.util.List;
import java.util.UUID;

public interface PostServices {
    List<Post> getAllPosts();
    Post getPostById(UUID id);
    Post postPost(String title, String content);
    Post putPost(UUID id, String title, String content);
    void deletePost(UUID id);
}
