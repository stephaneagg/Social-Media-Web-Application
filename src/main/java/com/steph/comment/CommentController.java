package com.steph.comment;


import com.steph.comment.DTOs.CommentDTO;
import com.steph.comment.DTOs.CommentDTOMapper;
import com.steph.comment.DTOs.CreateCommentDTO;
import com.steph.comment.DTOs.UpdateCommentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/post/{id}")
    public List<CommentDTO> getComments(@PathVariable Integer id) {
        return commentService.getComments(id);
    }

    @PostMapping("/post/{postId}")
    public CommentDTO createComment(@RequestBody CreateCommentDTO createCommentDTO, @PathVariable Integer postId) {
        return commentService.createComment(createCommentDTO, postId);
    }

    @PutMapping("{id}")
    public CommentDTO updateComment(@RequestBody UpdateCommentDTO updateCommentDTO, @PathVariable Integer id) {
        return commentService.updateComment(updateCommentDTO, id);
    }
    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
    }

}
