package com.steph.comment;


import com.steph.comment.DTOs.CommentDTO;
import com.steph.comment.DTOs.CommentDTOMapper;
import com.steph.comment.DTOs.CreateCommentDTO;
import com.steph.comment.DTOs.UpdateCommentDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    // Get list of all comments belonging to a post
    @GetMapping("/post/{postId}")
    public List<CommentDTO> getComments(@PathVariable Integer postId) {
        return commentService.getComments(postId);
    }

    // Create a new comment
    @PostMapping("/post/{postId}")
    public CommentDTO createComment(@RequestBody CreateCommentDTO createCommentDTO,
                                    @PathVariable Integer postId,
                                    @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId) {
        return commentService.createComment(createCommentDTO, postId, authenticatedUserId);
    }

    // Update an existing comment
    @PutMapping("{commentId}")
    public CommentDTO updateComment(@RequestBody UpdateCommentDTO updateCommentDTO,
                                    @PathVariable Integer commentId,
                                    @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws AccessDeniedException {
        return commentService.updateComment(updateCommentDTO, commentId, authenticatedUserId);
    }

    // Delete an existing comment
    @DeleteMapping("{commentId}")
    public void deleteComment(@PathVariable Integer commentId,
                              @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws AccessDeniedException {
        commentService.deleteComment(commentId, authenticatedUserId);
    }

}
