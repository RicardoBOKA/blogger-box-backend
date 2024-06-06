package com.dauphine.blogger.repository;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("""
        SELECT post
        FROM Post post
        WHERE UPPER(post.title) LIKE UPPER(CONCAT('%', :title, '%'))
""")
    List<Post> findAllLikeNames(@Param("title") String title);
}
