package com.steph.comment.DTOs;

public record CreateCommentDTO(
        Integer postId,
        String content
) {
}
