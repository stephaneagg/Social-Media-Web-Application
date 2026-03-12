package com.steph.follow;

import com.steph.config.JwtService;
import com.steph.follows.FollowController;
import com.steph.follows.FollowService;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
@AutoConfigureMockMvc(addFilters = false)
class FollowControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FollowService followService;

    @MockBean
    private JwtService jwtService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getFollowers_shouldReturnListOfFollowers() throws Exception {

        UserProfileDTO user1 = new UserProfileDTO(1, "user1", null, null);
        UserProfileDTO user2 = new UserProfileDTO(2, "user2", null, null);

        when(followService.getFollowers(10)).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/follows/followers/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(followService).getFollowers(10);
    }

    @Test
    void getFollows_shouldReturnUsersBeingFollowed() throws Exception {

        UserProfileDTO user1 = new UserProfileDTO(3, "user3", null, null);

        when(followService.getFollows(5)).thenReturn(List.of(user1));

        mockMvc.perform(get("/follows/following/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(3));

        verify(followService).getFollows(5);
    }

    @Test
    void follow_shouldCreateFollowRelationship() throws Exception {

        User user = new User(1);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(auth);

        when(followService.follow(2, 1))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/follows/2"))
                .andExpect(status().isOk());

        verify(followService).follow(2, 1);
    }

    @Test
    void unfollow_shouldDeleteFollowRelationship() throws Exception {

        User user = new User(1);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(auth);

        when(followService.unfollow(2, 1))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/follows/2"))
                .andExpect(status().isOk());

        verify(followService).unfollow(2, 1);
    }


}
