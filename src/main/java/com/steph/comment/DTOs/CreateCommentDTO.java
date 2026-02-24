package com.steph.comment.DTOs;

public record CreateCommentDTO(
        Integer userId,
        Integer postId,
        String content
) {
}
