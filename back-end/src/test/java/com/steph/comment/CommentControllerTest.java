package com.steph.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steph.comment.DTOs.CommentDTO;
import com.steph.comment.DTOs.CreateCommentDTO;
import com.steph.comment.DTOs.UpdateCommentDTO;
import com.steph.config.JwtAuthenticationFilter;
import com.steph.user.Role;
import com.steph.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getComments_returnsCommentsForPost() throws Exception {
        CommentDTO first = new CommentDTO(1, 2, 10, "hi", Instant.parse("2026-02-01T00:00:00Z"));
        CommentDTO second = new CommentDTO(2, 3, 10, "hey", Instant.parse("2026-02-02T00:00:00Z"));

        when(commentService.getComments(10)).thenReturn(List.of(first, second));

        mockMvc.perform(get("/comments/post/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].content").value("hi"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].content").value("hey"));

        verify(commentService).getComments(10);
    }

    @Test
    void createComment_returnsCreatedComment() throws Exception {
        authenticateAs(5);
        CreateCommentDTO request = new CreateCommentDTO(10, "Nice post");
        CommentDTO created = new CommentDTO(3, 5, 10, "Nice post", Instant.parse("2026-03-01T00:00:00Z"));

        when(commentService.createComment(any(CreateCommentDTO.class), eq(10), eq(5))).thenReturn(created);

        mockMvc.perform(post("/comments/post/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.userId").value(5))
                .andExpect(jsonPath("$.postId").value(10))
                .andExpect(jsonPath("$.content").value("Nice post"));

        verify(commentService).createComment(any(CreateCommentDTO.class), eq(10), eq(5));
    }

    @Test
    void updateComment_returnsUpdatedComment() throws Exception {
        authenticateAs(5);
        UpdateCommentDTO request = new UpdateCommentDTO();
        request.setContent("updated");
        CommentDTO updated = new CommentDTO(3, 5, 10, "updated", Instant.parse("2026-04-01T00:00:00Z"));

        when(commentService.updateComment(any(UpdateCommentDTO.class), eq(3), eq(5))).thenReturn(updated);

        mockMvc.perform(put("/comments/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.content").value("updated"));

        verify(commentService).updateComment(any(UpdateCommentDTO.class), eq(3), eq(5));
    }

    @Test
    void deleteComment_callsService() throws Exception {
        authenticateAs(5);

        mockMvc.perform(delete("/comments/3"))
                .andExpect(status().isOk());

        verify(commentService).deleteComment(3, 5);
    }

    private void authenticateAs(Integer userId) {
        User principal = new User("user" + userId, "user" + userId + "@example.com", "password", Role.USER);
        principal.setId(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
