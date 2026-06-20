package com.steph.search;

import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.PostDTOMapper;
import com.steph.post.Post;
import com.steph.post.PostRepository;
import com.steph.search.DTOs.SearchResultsDTO;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.DTOs.UserProfileDTOMapper;
import com.steph.user.User;
import com.steph.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserProfileDTOMapper userProfileDTOMapper;

    @Mock
    private PostDTOMapper postDTOMapper;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_shouldReturnMatchingUsersAndPosts_whenQueryMatchesBoth() {
        String query = "steph";

        User user = mock(User.class);
        UserProfileDTO userDTO = new UserProfileDTO(1, "stephane", null, null, null);

        Post post = mock(Post.class);
        PostDTO postDTO = new PostDTO(1, 1, "stephane", "steph's content", null, null, 0, Instant.now());

        when(userRepository.findByDisplayNameContainingIgnoreCase(query)).thenReturn(List.of(user));
        when(userProfileDTOMapper.apply(user)).thenReturn(userDTO);

        when(postRepository.findByContentTextContainingIgnoreCase(query)).thenReturn(List.of(post));
        when(postDTOMapper.apply(post)).thenReturn(postDTO);

        SearchResultsDTO result = searchService.search(query);

        assertEquals(1, result.users().size());
        assertEquals(userDTO, result.users().get(0));
        assertEquals(1, result.posts().size());
        assertEquals(postDTO, result.posts().get(0));

        verify(userRepository).findByDisplayNameContainingIgnoreCase(query);
        verify(userProfileDTOMapper).apply(user);
        verify(postRepository).findByContentTextContainingIgnoreCase(query);
        verify(postDTOMapper).apply(post);
    }

    @Test
    void search_shouldReturnEmptyUsersList_whenNoUsersMatch() {
        String query = "nomatch";

        Post post = mock(Post.class);
        PostDTO postDTO = new PostDTO(1, 1, "name", "content", null, null, 0, Instant.now());

        when(userRepository.findByDisplayNameContainingIgnoreCase(query)).thenReturn(List.of());
        when(postRepository.findByContentTextContainingIgnoreCase(query)).thenReturn(List.of(post));
        when(postDTOMapper.apply(post)).thenReturn(postDTO);

        SearchResultsDTO result = searchService.search(query);

        assertTrue(result.users().isEmpty());
        assertEquals(1, result.posts().size());

        verify(userRepository).findByDisplayNameContainingIgnoreCase(query);
        verifyNoInteractions(userProfileDTOMapper);
    }

    @Test
    void search_shouldReturnEmptyPostsList_whenNoPostsMatch() {
        String query = "nomatch";

        User user = mock(User.class);
        UserProfileDTO userDTO = new UserProfileDTO(1, "name", null, null, null);

        when(userRepository.findByDisplayNameContainingIgnoreCase(query)).thenReturn(List.of(user));
        when(userProfileDTOMapper.apply(user)).thenReturn(userDTO);
        when(postRepository.findByContentTextContainingIgnoreCase(query)).thenReturn(List.of());

        SearchResultsDTO result = searchService.search(query);

        assertEquals(1, result.users().size());
        assertTrue(result.posts().isEmpty());

        verify(postRepository).findByContentTextContainingIgnoreCase(query);
        verifyNoInteractions(postDTOMapper);
    }

    @Test
    void search_shouldReturnEmptyResults_whenNothingMatches() {
        String query = "nomatch";

        when(userRepository.findByDisplayNameContainingIgnoreCase(query)).thenReturn(List.of());
        when(postRepository.findByContentTextContainingIgnoreCase(query)).thenReturn(List.of());

        SearchResultsDTO result = searchService.search(query);

        assertTrue(result.users().isEmpty());
        assertTrue(result.posts().isEmpty());

        verifyNoInteractions(userProfileDTOMapper);
        verifyNoInteractions(postDTOMapper);
    }


}