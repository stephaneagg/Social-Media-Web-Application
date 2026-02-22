package com.steph.post;

import com.steph.post.DTOs.CreatePostDTO;
import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.UpdatePostDTO;
import org.springframework.web.bind.annotation.*;

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
    public PostDTO createPost(@RequestBody CreatePostDTO createPostDTO) {
        return postService.createPost(createPostDTO);
    }

    // Edit a post
    @PutMapping("/{id}")
    public PostDTO updatePost(@RequestBody UpdatePostDTO updatePostDTO, @PathVariable Integer id) {
        return postService.updatePost(updatePostDTO, id);
    }


    // Delete a post
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
    }
}
