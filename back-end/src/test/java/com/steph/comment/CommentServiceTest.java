package com.steph.comment;

import com.steph.comment.DTOs.CommentDTO;
import com.steph.comment.DTOs.CommentDTOMapper;
import com.steph.comment.DTOs.CreateCommentDTO;
import com.steph.comment.DTOs.UpdateCommentDTO;
import com.steph.exceptions.CommentException;
import com.steph.exceptions.PostException;
import com.steph.exceptions.UserException;
import com.steph.post.Post;
import com.steph.post.PostRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentDTOMapper commentDTOMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void getComments_returnsMappedComments() {
        Comment first = new Comment(1, new User(2), new Post(10, new User(9), "p1", null, Instant.parse("2026-01-01T00:00:00Z")), "hi", Instant.parse("2026-02-01T00:00:00Z"));
        Comment second = new Comment(2, new User(3), new Post(10, new User(8), "p2", null, Instant.parse("2026-01-02T00:00:00Z")), "hey", Instant.parse("2026-02-02T00:00:00Z"));
        CommentDTO dto1 = new CommentDTO(1, 2, 10, "hi", Instant.parse("2026-02-01T00:00:00Z"));
        CommentDTO dto2 = new CommentDTO(2, 3, 10, "hey", Instant.parse("2026-02-02T00:00:00Z"));

        when(commentRepository.findByPostIdOrderByCreatedAtDesc(10)).thenReturn(List.of(first, second));
        when(commentDTOMapper.apply(any(Comment.class))).thenReturn(dto1, dto2);

        List<CommentDTO> result = commentService.getComments(10);

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        verify(commentRepository).findByPostIdOrderByCreatedAtDesc(10);
        verify(commentDTOMapper, times(2)).apply(any(Comment.class));
    }

    @Test
    void createComment_savesComment_whenUserAndPostExist() {
        Integer userId = 5;
        Integer postId = 10;
        CreateCommentDTO createDTO = new CreateCommentDTO(postId, "Nice post");

        User user = new User(userId);
        Post post = new Post(postId, new User(20), "p", null, Instant.parse("2026-01-01T00:00:00Z"));
        CommentDTO mapped = new CommentDTO(3, userId, postId, "Nice post", Instant.parse("2026-03-01T00:00:00Z"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentDTOMapper.apply(any(Comment.class))).thenReturn(mapped);

        CommentDTO result = commentService.createComment(createDTO, postId, userId);

        assertEquals(mapped, result);

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());
        Comment saved = captor.getValue();
        assertEquals(user, saved.getUser());
        assertEquals(post, saved.getPost());
        assertEquals("Nice post", saved.getContent());
        verify(commentDTOMapper).apply(saved);
    }

    @Test
    void createComment_throwsUserException_whenUserNotFound() {
        Integer userId = 5;
        Integer postId = 10;
        CreateCommentDTO createDTO = new CreateCommentDTO(postId, "Nice post");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> commentService.createComment(createDTO, postId, userId));
        verify(userRepository).findById(userId);
        verifyNoInteractions(postRepository);
        verifyNoInteractions(commentRepository);
        verifyNoInteractions(commentDTOMapper);
    }

    @Test
    void createComment_throwsPostException_whenPostNotFound() {
        Integer userId = 5;
        Integer postId = 10;
        CreateCommentDTO createDTO = new CreateCommentDTO(postId, "Nice post");

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User(userId)));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        PostException exception = assertThrows(PostException.class, () -> commentService.createComment(createDTO, postId, userId));
        assertEquals("10 not found", exception.getMessage());
        verify(postRepository).findById(postId);
        verifyNoInteractions(commentRepository);
        verifyNoInteractions(commentDTOMapper);
    }

    @Test
    void updateComment_updatesWhenOwnerMatches() throws Exception {
        Integer commentId = 3;
        Integer userId = 5;
        UpdateCommentDTO updateDTO = new UpdateCommentDTO();
        updateDTO.setContent("updated");

        Comment comment = mock(Comment.class);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getUser()).thenReturn(new User(userId));
        CommentDTO mapped = new CommentDTO(commentId, userId, 10, "updated", Instant.parse("2026-04-01T00:00:00Z"));
        when(commentDTOMapper.apply(comment)).thenReturn(mapped);

        CommentDTO result = commentService.updateComment(updateDTO, commentId, userId);

        assertEquals(mapped, result);
        verify(comment).updateComment(updateDTO);
        verify(commentRepository).save(comment);
        verify(commentDTOMapper).apply(comment);
    }

    @Test
    void updateComment_throwsCommentException_whenNotFound() {
        Integer commentId = 3;
        Integer userId = 5;
        UpdateCommentDTO updateDTO = new UpdateCommentDTO();
        updateDTO.setContent("updated");

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        CommentException exception = assertThrows(CommentException.class, () -> commentService.updateComment(updateDTO, commentId, userId));
        assertEquals("Comment Id: 3 not found", exception.getMessage());
        verify(commentRepository).findById(commentId);
        verifyNoMoreInteractions(commentRepository);
        verifyNoInteractions(commentDTOMapper);
    }

    @Test
    void updateComment_throwsAccessDenied_whenOwnerMismatch() {
        Integer commentId = 3;
        UpdateCommentDTO updateDTO = new UpdateCommentDTO();
        updateDTO.setContent("updated");

        Comment comment = mock(Comment.class);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getUser()).thenReturn(new User(99));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> commentService.updateComment(updateDTO, commentId, 5));
        assertEquals("You can only update comments that belong to you", exception.getMessage());
        verify(commentRepository).findById(commentId);
        verify(comment).getUser();
        verifyNoMoreInteractions(commentRepository);
        verifyNoInteractions(commentDTOMapper);
    }

    @Test
    void deleteComment_deletesWhenOwnerMatches() throws Exception {
        Integer commentId = 3;
        Integer userId = 5;

        Comment comment = mock(Comment.class);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getUser()).thenReturn(new User(userId));

        commentService.deleteComment(commentId, userId);

        verify(commentRepository).deleteById(commentId);
    }

    @Test
    void deleteComment_throwsCommentException_whenNotFound() {
        Integer commentId = 3;
        Integer userId = 5;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        CommentException exception = assertThrows(CommentException.class, () -> commentService.deleteComment(commentId, userId));
        assertEquals("Comment Id: 3 not found", exception.getMessage());
        verify(commentRepository).findById(commentId);
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    void deleteComment_throwsAccessDenied_whenOwnerMismatch() {
        Integer commentId = 3;

        Comment comment = mock(Comment.class);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getUser()).thenReturn(new User(99));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> commentService.deleteComment(commentId, 5));
        assertEquals("You can only update comments that belong to you", exception.getMessage());
        verify(commentRepository).findById(commentId);
        verify(comment).getUser();
        verifyNoMoreInteractions(commentRepository);
    }
}
