package com.steph.comment.DTOs;

import java.time.Instant;

public record CommentDTO(
        Integer id,
        Integer userId,
        Integer postId,
        String content,
        Instant createdAt
) {
}
