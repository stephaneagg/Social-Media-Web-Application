package com.steph.comment;

import com.steph.comment.DTOs.CommentDTO;
import com.steph.comment.DTOs.CommentDTOMapper;
import com.steph.comment.DTOs.CreateCommentDTO;
import com.steph.comment.DTOs.UpdateCommentDTO;
import com.steph.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private CommentDTOMapper commentDTOMapper;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, CommentDTOMapper commentDTOMapper, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentDTOMapper = commentDTOMapper;
        this.postRepository = postRepository;
    }

    public List<CommentDTO> getComment(Integer id) {
    }

    public CommentDTO createComment(CreateCommentDTO createCommentDTO, Integer postId) {
    }

    public CommentDTO updateComment(UpdateCommentDTO updateCommentDTO, Integer id) {
    }

    public void deleteComment(Integer id) {
    }
}
