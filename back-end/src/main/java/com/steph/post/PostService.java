package com.steph.post;


import com.steph.exceptions.PostException;
import com.steph.post.DTOs.CreatePostDTO;
import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.PostDTOMapper;
import com.steph.post.DTOs.UpdatePostDTO;
import com.steph.user.User;
import com.steph.user.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostDTOMapper postDTOMapper;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostDTOMapper postDTOMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postDTOMapper = postDTOMapper;
        this.userRepository = userRepository;
    }

    public PostDTO getPost(Integer id){
        return postRepository.findById(id)
                .map(postDTOMapper)
                .orElseThrow(() -> new PostException(id + " not found"));
    }

    public List<PostDTO> getPostsByUser(Integer userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(postDTOMapper)
                .collect(Collectors.toList());
    }

    public PostDTO createPost(CreatePostDTO createPostDTO, Integer authenticatedUserId) {
        // find user given userId
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new PostException("User Id: "+ authenticatedUserId + " not found"));

        // create a new post using info passed in creatPostDTO
        Post post = new Post();
        post.setUser(user);
        post.setContentText(createPostDTO.content());
        post.setImageUrl(createPostDTO.imageUrl());

        // save post to DB
        postRepository.save(post);
        // map post to postDTO and return
        return postDTOMapper.apply(post);
    }

    public PostDTO updatePost(UpdatePostDTO updatePostDTO,
                              Integer postId,
                              Integer authenticatedUserId)
            throws AccessDeniedException {
        // retrieve the post we want to update
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(postId + " not found"));

        // Check the post belongs to the authenticated user
        if (!post.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update posts for your own profile");
        }

        // update the post
        post.updatePost(updatePostDTO);
        postRepository.save(post);
        // map post to postDTO and return
        return postDTOMapper.apply(post);
    }

    public void deletePost(Integer postId, Integer authenticatedUserId)
            throws AccessDeniedException {

        // retrieve the post we want to update
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(postId + " not found"));

        // Check the post belongs to the authenticated user
        if (!post.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only delete posts for your own profile");
        }

        postRepository.deleteById(postId);
    }
}
