package com.steph.post.DTOs;

public record CreatePostDTO(
        Integer authorId,
        String content,
        String imageUrl
) {
}
