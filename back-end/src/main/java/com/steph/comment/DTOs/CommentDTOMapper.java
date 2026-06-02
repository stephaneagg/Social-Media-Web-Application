package com.steph.comment.DTOs;

import com.steph.comment.Comment;
import com.steph.comment.CommentRepository;
import com.steph.upload.ImageUrlNormalizer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<Comment, CommentDTO> {

    private final ImageUrlNormalizer imageUrlNormalizer;


    public CommentDTOMapper(ImageUrlNormalizer imageUrlNormalizer) {
        this.imageUrlNormalizer = imageUrlNormalizer;
    }


    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                imageUrlNormalizer.normalize(comment.getUser().getProfileImageUrl()),
                comment.getCreatedAt()
        );
    }
}