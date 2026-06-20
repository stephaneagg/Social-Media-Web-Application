package com.steph.search;

import com.steph.config.JwtAuthenticationFilter;
import com.steph.post.DTOs.PostDTO;
import com.steph.search.DTOs.SearchResultsDTO;
import com.steph.user.DTOs.UserProfileDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
@AutoConfigureMockMvc(addFilters = false) // disables security filters
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    // Mock security beans to prevent UnsatisfiedDependencyException
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void search_shouldReturnUsersAndPosts_whenQueryMatches() throws Exception {
        UserProfileDTO user = new UserProfileDTO(1, "steph", null, null, null);
        PostDTO post = new PostDTO(1, 1, "steph", "steph's post", null, null, 0, Instant.now());

        SearchResultsDTO resultsDTO = new SearchResultsDTO(List.of(user), List.of(post));

        when(searchService.search(eq("steph"))).thenReturn(resultsDTO);

        mockMvc.perform(get("/search").param("query", "steph"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users.length()").value(1))
                .andExpect(jsonPath("$.users[0].displayName").value("steph"))
                .andExpect(jsonPath("$.posts.length()").value(1))
                .andExpect(jsonPath("$.posts[0].content").value("steph's post"));
    }

    @Test
    void search_shouldReturnEmptyResults_whenNothingMatches() throws Exception {
        SearchResultsDTO resultsDTO = new SearchResultsDTO(List.of(), List.of());

        when(searchService.search(eq("nomatch"))).thenReturn(resultsDTO);

        mockMvc.perform(get("/search").param("query", "nomatch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users.length()").value(0))
                .andExpect(jsonPath("$.posts.length()").value(0));
    }

    @Test
    void search_shouldReturnBadRequest_whenQueryParamIsMissing() throws Exception {
        mockMvc.perform(get("/search"))
                .andExpect(status().isBadRequest());
    }
}