package com.steph.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserIdOrderByCreatedAtDesc(Integer userId);

    @Query("SELECT p " +
            "FROM Post p " +
            "JOIN FETCH p.user " +
            "JOIN Follow f ON p.user.id = f.followed.id " +
            "WHERE f.follower.id = :userId " +
            "ORDER BY p.createdAt DESC")
    List<Post> findFeedPosts(@Param("userId") Integer userId);
}
