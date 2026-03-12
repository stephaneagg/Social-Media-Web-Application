package com.steph.post;

import com.steph.exceptions.PostException;
import com.steph.post.DTOs.CreatePostDTO;
import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.PostDTOMapper;
import com.steph.post.DTOs.UpdatePostDTO;
import com.steph.user.User;
import com.steph.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostDTOMapper postDTOMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void getPost_shouldReturnPostDTO_whenPostExists() {
        Integer postId = 1;
        Post post = new Post();
        PostDTO dto = new PostDTO(1, 10, "steph", "hello", "https://img", Instant.parse("2026-01-01T10:00:00Z"));

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postDTOMapper.apply(post)).thenReturn(dto);

        PostDTO result = postService.getPost(postId);

        assertEquals(dto, result);
        verify(postRepository).findById(postId);
        verify(postDTOMapper).apply(post);
    }

    @Test
    void getPost_shouldThrowPostException_whenPostDoesNotExist() {
        Integer postId = 99;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        PostException exception = assertThrows(PostException.class, () -> postService.getPost(postId));

        assertEquals("99 not found", exception.getMessage());
        verify(postRepository).findById(postId);
        verifyNoInteractions(postDTOMapper);
    }

    @Test
    void getPostsByUser_shouldReturnListOfPostDTOs_whenPostsExist() {
        Integer userId = 10;

        Post post1 = new Post();
        Post post2 = new Post();
        PostDTO dto1 = new PostDTO(1, 10, "steph", "latest", "https://img1", Instant.parse("2026-01-02T10:00:00Z"));
        PostDTO dto2 = new PostDTO(2, 10, "steph", "older", "https://img2", Instant.parse("2026-01-01T10:00:00Z"));

        when(postRepository.findByUserIdOrderByCreatedAtDesc(userId)).thenReturn(List.of(post1, post2));
        when(postDTOMapper.apply(any(Post.class))).thenReturn(dto1, dto2);

        List<PostDTO> result = postService.getPostsByUser(userId);

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        verify(postRepository).findByUserIdOrderByCreatedAtDesc(userId);
        verify(postDTOMapper, org.mockito.Mockito.times(2)).apply(any(Post.class));
    }

    @Test
    void getPostsByUser_shouldReturnEmptyList_whenNoPostsExist() {
        Integer userId = 10;

        when(postRepository.findByUserIdOrderByCreatedAtDesc(userId)).thenReturn(List.of());

        List<PostDTO> result = postService.getPostsByUser(userId);

        assertTrue(result.isEmpty());
        verify(postRepository).findByUserIdOrderByCreatedAtDesc(userId);
        verifyNoInteractions(postDTOMapper);
    }

    @Test
    void createPost_shouldReturnCreatedPostDTO_whenUserExists() {
        Integer userId = 10;
        Integer authenticatedUserId = 10;
        CreatePostDTO createPostDTO = new CreatePostDTO("new post", "https://img");

        User user = new User(userId);

        PostDTO mappedDTO = new PostDTO(3, 10, "steph", "new post", "https://img", Instant.parse("2026-01-03T10:00:00Z"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postDTOMapper.apply(any(Post.class))).thenReturn(mappedDTO);

        PostDTO result = assertDoesNotThrow(() -> postService.createPost(createPostDTO, authenticatedUserId));

        assertEquals(mappedDTO, result);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postCaptor.capture());
        Post savedPost = postCaptor.getValue();

        assertEquals(user, savedPost.getUser());
        assertEquals("new post", savedPost.getContentText());
        assertEquals("https://img", savedPost.getImageUrl());

        verify(userRepository).findById(userId);
        verify(postDTOMapper).apply(savedPost);
    }

    @Test
    void createPost_shouldThrowPostException_whenUserDoesNotExist() {
        Integer userId = 77;
        Integer authenticatedUserId = 77;
        CreatePostDTO createPostDTO = new CreatePostDTO("content", "https://img");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        PostException exception = assertThrows(PostException.class, () -> postService.createPost(createPostDTO, authenticatedUserId));

        assertEquals("77 not found", exception.getMessage());
        verify(userRepository).findById(userId);
        verifyNoInteractions(postRepository);
        verifyNoInteractions(postDTOMapper);
    }

    @Test
    void updatePost_shouldReturnUpdatedPostDTO_whenPostExists() {
        Integer postId = 1;
        Integer authenticatedUserId = 10;

        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setContent("updated");
        updatePostDTO.setImageUrl("https://new-img");

        User user = new User(authenticatedUserId);

        Post post = mock(Post.class);
        PostDTO mappedDTO = new PostDTO(1, 10, "steph", "updated", "https://new-img", Instant.parse("2026-01-04T10:00:00Z"));

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(post.getUser()).thenReturn(user);
        when(postDTOMapper.apply(post)).thenReturn(mappedDTO);

        PostDTO result = assertDoesNotThrow(() -> postService.updatePost(updatePostDTO, postId, authenticatedUserId));

        assertEquals(mappedDTO, result);
        verify(postRepository).findById(postId);
        verify(post).updatePost(updatePostDTO);
        verify(postRepository).save(post);
        verify(postDTOMapper).apply(post);
    }

    @Test
    void updatePost_shouldThrowPostException_whenPostDoesNotExist() {
        Integer postId = 88;
        Integer authenticatedUserId = 10;

        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setContent("updated");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        PostException exception = assertThrows(PostException.class, () -> postService.updatePost(updatePostDTO, postId, authenticatedUserId));

        assertEquals("88 not found", exception.getMessage());
        verify(postRepository).findById(postId);
        verifyNoMoreInteractions(postRepository);
        verifyNoInteractions(postDTOMapper);
    }

    @Test
    void deletePost_shouldCallRepository() {
        Integer postId = 1;
        Integer authenticatedUserId = 10;
        User user = new User(authenticatedUserId);


        Post post = mock(Post.class);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(post.getUser()).thenReturn(user);

        assertDoesNotThrow(() -> postService.deletePost(postId, authenticatedUserId));

        verify(postRepository).deleteById(postId);
    }

    @Test
    void createPost_shouldThrowAccessDeniedException_whenCreatingPostForAnotherUser() {
        Integer authorId = 10;
        Integer authenticatedUserId = 11;
        CreatePostDTO createPostDTO = new CreatePostDTO("content", "https://img");

        User user = new User(authorId);
        when(userRepository.findById(authorId)).thenReturn(Optional.of(user));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                postService.createPost(createPostDTO, authenticatedUserId)
        );

        assertEquals("You can only create posts for your own profile", exception.getMessage());
        verify(userRepository).findById(authorId);
        verifyNoInteractions(postRepository);
        verifyNoInteractions(postDTOMapper);
    }

    @Test
    void updatePost_shouldThrowAccessDeniedException_whenUpdatingAnotherUsersPost() {
        Integer postId = 1;
        Integer ownerId = 10;
        Integer authenticatedUserId = 11;
        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setContent("updated");

        User owner = new User(ownerId);
        Post post = mock(Post.class);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(post.getUser()).thenReturn(owner);

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                postService.updatePost(updatePostDTO, postId, authenticatedUserId)
        );

        assertEquals("You can only update posts for your own profile", exception.getMessage());
        verify(postRepository).findById(postId);
        verify(post).getUser();
        verifyNoMoreInteractions(postRepository);
        verifyNoInteractions(postDTOMapper);
    }

    @Test
    void deletePost_shouldThrowAccessDeniedException_whenDeletingAnotherUsersPost() {
        Integer postId = 1;
        Integer ownerId = 10;
        Integer authenticatedUserId = 11;

        User owner = new User(ownerId);
        Post post = mock(Post.class);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(post.getUser()).thenReturn(owner);

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                postService.deletePost(postId, authenticatedUserId)
        );

        assertEquals("You can only delete posts for your own profile", exception.getMessage());
        verify(postRepository).findById(postId);
        verify(post).getUser();
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    void deletePost_shouldThrowPostException_whenPostDoesNotExist() {
        Integer postId = 88;
        Integer authenticatedUserId = 10;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        PostException exception = assertThrows(PostException.class, () ->
                postService.deletePost(postId, authenticatedUserId)
        );

        assertEquals("88 not found", exception.getMessage());
        verify(postRepository).findById(postId);
        verifyNoMoreInteractions(postRepository);
    }
}
