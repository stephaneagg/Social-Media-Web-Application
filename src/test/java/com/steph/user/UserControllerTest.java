package com.steph.user;

import com.steph.user.DTOs.UpdateUserDTO;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.config.JwtAuthenticationFilter;
import com.steph.config.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false) // disables security filters
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // for JSON serialization

    // Mock the service your controller uses
    @MockBean
    private UserService userService;

    // Mock security beans to prevent UnsatisfiedDependencyException
    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void getUsers_shouldReturnListOfUsers() throws Exception {
        UserProfileDTO user1 = new UserProfileDTO(1, "steph", null, null);
        UserProfileDTO user2 = new UserProfileDTO(2, "steph2", null, null);

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].displayName").value("steph"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].displayName").value("steph2"));
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() throws Exception {
        UserProfileDTO user = new UserProfileDTO(1, "steph", null, null);
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.displayName").value("steph"));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        UpdateUserDTO updateDTO = new UpdateUserDTO("newName", "newBio", "newImage");
        UserProfileDTO updatedUser = new UserProfileDTO(1, "newName", "newBio", "newImage");

        // <-- use argument matchers
        when(userService.updateUser(any(UpdateUserDTO.class), eq(1))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.displayName").value("newName"))
                .andExpect(jsonPath("$.bio").value("newBio"))
                .andExpect(jsonPath("$.profileImageUrl").value("newImage"));
    }
}