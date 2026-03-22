package com.steph.comment;

import com.steph.comment.DTOs.CommentDTO;
import com.steph.comment.DTOs.CommentDTOMapper;
import com.steph.comment.DTOs.CreateCommentDTO;
import com.steph.comment.DTOs.UpdateCommentDTO;
import com.steph.exceptions.CommentException;
import com.steph.exceptions.PostException;
import com.steph.exceptions.UserException;
import com.steph.post.PostRepository;
import com.steph.user.User;
import com.steph.post.Post;
import com.steph.user.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDTOMapper commentDTOMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository,
                          CommentDTOMapper commentDTOMapper,
                          UserRepository userRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentDTOMapper = commentDTOMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // Get list of comments belonging to a post
    public List<CommentDTO> getComments(Integer postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map(commentDTOMapper)
                .collect(Collectors.toList());
    }

    // Create a new comment
    public CommentDTO createComment(CreateCommentDTO createCommentDTO, Integer postId, Integer authenticatedUserId) {
        Comment comment = new Comment();

        // Get relevant user and set it to comment's user
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new UserException("User Id: " + authenticatedUserId + " not found"));
        comment.setUser(user);

        // get relevant post and set it to comment's post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(postId + " not found"));
        comment.setPost(post);

        comment.setContent(createCommentDTO.content());

        commentRepository.save(comment);

        return commentDTOMapper.apply(comment);
    }

    // Update an existing comment
    public CommentDTO updateComment(UpdateCommentDTO updateCommentDTO, Integer commentId, Integer authenticatedUserId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment Id: " + commentId + " not found"));

        // Check the comment belongs to the authenticated user
        if (!comment.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update comments that belong to you");
        }

        comment.updateComment(updateCommentDTO);
        commentRepository.save(comment);
        return commentDTOMapper.apply(comment);
    }

    // Delete an existing comment
    public void deleteComment(Integer commentId, Integer authenticatedUserId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment Id: " + commentId + " not found"));

        // Check the comment belongs to the authenticated user
        if (!comment.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update comments that belong to you");
        }

        commentRepository.deleteById(commentId);
    }
}
