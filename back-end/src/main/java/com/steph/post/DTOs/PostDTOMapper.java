package com.steph.post.DTOs;


import com.steph.comment.CommentRepository;
import com.steph.post.Post;
import com.steph.upload.ImageUrlNormalizer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostDTOMapper implements Function<Post, PostDTO> {

    private final CommentRepository commentRepository;
    private final ImageUrlNormalizer imageUrlNormalizer;

    public PostDTOMapper(CommentRepository commentRepository, ImageUrlNormalizer imageUrlNormalizer) {
        this.commentRepository = commentRepository;
        this.imageUrlNormalizer = imageUrlNormalizer;
    }

    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getId(), // id
                post.getUser().getId(), // authorId
                post.getUser().getDisplayName(), // authorName
                post.getContentText(), // content
                imageUrlNormalizer.normalize(post.getUser().getProfileImageUrl()), //userProfileImageUrl
                imageUrlNormalizer.normalize(post.getImageUrl()), // imageUrl
                commentRepository.countByPostId(post.getId()), // commentCount
                post.getCreatedAt() // createdAt
        );
    }
}
