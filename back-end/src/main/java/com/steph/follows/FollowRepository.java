package com.steph.follows;

import com.steph.follows.projections.MutualSuggestionProjection;
import com.steph.follows.projections.PopularSuggestionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    List<Follow> findByFollowedId(Integer followedId);
    List<Follow> findByFollowerId(Integer followerId);

    @Query(value = """
        SELECT
            u.id AS userId,
            u.display_name AS displayName,
            u.profile_image_url AS profileImageUrl,
            COUNT(f2.follower_id) AS mutualCount
        FROM users u
        JOIN follows f2 ON f2.followed_id = u.id
        JOIN follows f1 ON f1.followed_id = f2.follower_id
                        AND f1.follower_id = :userId
        LEFT JOIN follows existing ON existing.follower_id = :userId
                                   AND existing.followed_id = u.id
        WHERE u.id != :userId
          AND existing.followed_id IS NULL
        GROUP BY u.id, u.display_name, u.profile_image_url
        ORDER BY mutualCount DESC, u.id ASC
        LIMIT :limit
        """, nativeQuery = true)
    List<MutualSuggestionProjection> findMutualFollowSuggestions(
            @Param("userId") Integer userId,
            @Param("limit") int limit);




    @Query(value = """
        SELECT
            u.id AS userId,
            u.display_name AS displayName,
            u.profile_image_url AS profileImageUrl,
            COUNT(f.follower_id) AS followerCount
        FROM users u
        JOIN follows f ON f.followed_id = u.id
        LEFT JOIN follows existing ON existing.follower_id = :userId
                                   AND existing.followed_id = u.id
        WHERE u.id != :userId
          AND existing.followed_id IS NULL
          AND u.id NOT IN (:excludedIds)
        GROUP BY u.id, u.display_name, u.profile_image_url
        ORDER BY followerCount DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<PopularSuggestionProjection> findPopularFollowSuggestions(
            @Param("userId") Integer userId,
            @Param("excludedIds") List<Integer> excludedIds,
            @Param("limit") int limit);
}
