package com.steph.post.DTOs;


import com.steph.post.Post;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostDTOMapper implements Function<Post, PostDTO> {

    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getId(), // id
                post.getUser().getId(), // authorId
                post.getUser().getDisplayName(), // authorName
                post.getContentText(), // content
                post.getImageUrl(), // imageUrl
                post.getCreatedAt() // createdAt
        );
    }
}
