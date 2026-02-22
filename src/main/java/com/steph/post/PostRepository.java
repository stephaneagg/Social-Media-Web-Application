package com.steph.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserIdOrderByCreatedAtDesc(Integer userId);
}
