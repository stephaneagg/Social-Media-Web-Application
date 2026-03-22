package com.steph.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steph.config.JwtAuthenticationFilter;
import com.steph.post.DTOs.CreatePostDTO;
import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.UpdatePostDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getPost_shouldReturnPostById() throws Exception {
        PostDTO post = new PostDTO(1, 10, "steph", "hello", "https://img", Instant.parse("2026-01-01T10:00:00Z"));
        when(postService.getPost(1)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.authorId").value(10))
                .andExpect(jsonPath("$.authorName").value("steph"))
                .andExpect(jsonPath("$.content").value("hello"))
                .andExpect(jsonPath("$.imageUrl").value("https://img"))
                .andExpect(jsonPath("$.createdAt").value("2026-01-01T10:00:00Z"));
    }

    @Test
    void getPostsByUser_shouldReturnPostsForUser() throws Exception {
        PostDTO first = new PostDTO(1, 10, "steph", "latest", "https://img1", Instant.parse("2026-01-02T10:00:00Z"));
        PostDTO second = new PostDTO(2, 10, "steph", "older", "https://img2", Instant.parse("2026-01-01T10:00:00Z"));

        when(postService.getPostsByUser(10)).thenReturn(List.of(first, second));

        mockMvc.perform(get("/posts/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].content").value("latest"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].content").value("older"));
    }

    @Test
    void createPost_shouldReturnCreatedPost() throws Exception {
        authenticateAs(10);

        CreatePostDTO request = new CreatePostDTO("new post", "https://img");
        PostDTO created = new PostDTO(3, 10, "steph", "new post", "https://img", Instant.parse("2026-01-03T10:00:00Z"));

        when(postService.createPost(any(CreatePostDTO.class), eq(10))).thenReturn(created);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.authorId").value(10))
                .andExpect(jsonPath("$.content").value("new post"));
    }

    @Test
    void updatePost_shouldReturnUpdatedPost() throws Exception {
        authenticateAs(10);

        UpdatePostDTO request = new UpdatePostDTO();
        request.setContent("updated");
        request.setImageUrl("https://new-img");

        PostDTO updated = new PostDTO(1, 10, "steph", "updated", "https://new-img", Instant.parse("2026-01-04T10:00:00Z"));

        when(postService.updatePost(any(UpdatePostDTO.class), eq(1), eq(10))).thenReturn(updated);

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("updated"))
                .andExpect(jsonPath("$.imageUrl").value("https://new-img"));
    }

    @Test
    void deletePost_shouldCallServiceAndReturnNoContent() throws Exception {
        authenticateAs(10);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService).deletePost(1, 10);
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
