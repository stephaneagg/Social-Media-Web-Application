package com.steph.post.DTOs;


import com.steph.comment.CommentRepository;
import com.steph.post.Post;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostDTOMapper implements Function<Post, PostDTO> {

    private final CommentRepository commentRepository;

    public PostDTOMapper(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getId(), // id
                post.getUser().getId(), // authorId
                post.getUser().getDisplayName(), // authorName
                post.getContentText(), // content
                post.getUser().getProfileImageUrl(), //userProfileImageUrl
                post.getImageUrl(), // imageUrl
                commentRepository.countByPostId(post.getId()), // commentCount
                post.getCreatedAt() // createdAt
        );
    }
}
