package com.steph.comment.DTOs;

public record CreateCommentDTO(
        Integer userID,
        Integer postID,
        String content
) {
}
