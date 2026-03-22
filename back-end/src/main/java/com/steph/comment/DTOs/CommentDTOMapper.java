package com.steph.comment.DTOs;

import com.steph.comment.Comment;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<Comment, CommentDTO> {
    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}