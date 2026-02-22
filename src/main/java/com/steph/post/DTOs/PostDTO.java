package com.steph.post.DTOs;

import java.time.Instant;

public record PostDTO(
        Integer id,
        Integer authorId,
        String authorName,
        String content,
        String imageUrl,
        Instant createdAt
) {
}
