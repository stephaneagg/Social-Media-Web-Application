package com.steph.follows.projections;

public interface PopularSuggestionProjection {
    Integer getUserId();
    String getDisplayName();
    String getProfileImageUrl();
}
//        SELECT
//            u.id AS userId,
//            u.display_name AS displayName,
//            u.profile_image_url AS profileImageUrl,
//            COUNT(f2.follower_id) AS mutualCount
//        FROM users u
//        JOIN follows f2 ON f2.followed_id = u.id
//        JOIN follows f1 ON f1.followed_id = f2.follower_id
//                        AND f1.follower_id = :userId
//        LEFT JOIN follows existing ON existing.follower_id = :userId
//                                   AND existing.followed_id = u.id
//        WHERE u.id != :userId
//          AND existing.followed_id IS NULL
//        GROUP BY u.id, u.display_name, u.profile_image_url
//        ORDER BY mutualCount DESC, u.id ASC
//        LIMIT :limit