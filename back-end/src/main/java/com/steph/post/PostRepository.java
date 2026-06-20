package com.steph.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserIdOrderByCreatedAtDesc(Integer userId);
    List<Post> findByContentTextContainingIgnoreCase(String query);

    @Query("""
    SELECT p
    FROM Post p
    JOIN FETCH p.user
    WHERE p.user.id = :userId
       OR EXISTS (
            SELECT 1
            FROM Follow f
            WHERE f.follower.id = :userId
              AND f.followed.id = p.user.id
       )
    ORDER BY p.createdAt DESC
""")
    List<Post> findFeedPosts(@Param("userId") Integer userId);
}
