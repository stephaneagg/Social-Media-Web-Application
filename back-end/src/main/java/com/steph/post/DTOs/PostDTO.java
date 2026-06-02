package com.steph.post.DTOs;

import java.time.Instant;

public record PostDTO(
        Integer id,
        Integer authorId,
        String authorName,
        String content,
        String userProfileImageUrl,
        String imageUrl,
        Integer commentCount,
        Instant createdAt
) {
}
