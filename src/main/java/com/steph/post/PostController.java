package com.steph.post;

import com.steph.post.DTOs.CreatePostDTO;
import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.UpdatePostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    // Get a single post
    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable Integer id) {
        return postService.getPost(id);
    }

    // Get posts by a user
    @GetMapping("/user/{userId}") // TODO: TEST THIS PATH WORKS AS EXPECTED
    public List<PostDTO> getPostsByUser(@PathVariable Integer userId) {
        return postService.getPostsByUser(userId);
    }

    // Create a post
    @PostMapping
    public PostDTO createPost(@RequestBody CreatePostDTO createPostDTO,
                              @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws AccessDeniedException {

        return postService.createPost(createPostDTO, authenticatedUserId);
    }

    // Edit a post
    @PutMapping("/{id}")
    public PostDTO updatePost(@RequestBody UpdatePostDTO updatePostDTO,
                              @PathVariable("id") Integer postId,
                              @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws AccessDeniedException {

        return postService.updatePost(updatePostDTO, postId, authenticatedUserId);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Integer id,
                           @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws AccessDeniedException {
        postService.deletePost(id, authenticatedUserId);
    }
}
