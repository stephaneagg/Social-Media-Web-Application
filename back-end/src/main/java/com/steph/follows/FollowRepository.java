package com.steph.follows;

import com.steph.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    List<Follow> findByFollowedId(Integer followedId);
    List<Follow> findByFollowerId(Integer followerId);
}
