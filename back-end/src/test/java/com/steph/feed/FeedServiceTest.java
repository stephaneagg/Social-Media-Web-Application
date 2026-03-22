package com.steph.feed;

import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.PostDTOMapper;
import com.steph.post.Post;
import com.steph.post.PostRepository;
import com.steph.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FeedServiceTest {

    private PostRepository postRepository;
    private PostDTOMapper postDTOMapper;
    private FeedService feedService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postDTOMapper = mock(PostDTOMapper.class);
        feedService = new FeedService(postRepository, postDTOMapper);
    }

    @Test
    void getFeed_returnsMappedPostsInOrder() {
        Integer userId = 5;

        Post first = new Post(1, new User(2), "latest", "https://img1", Instant.parse("2026-01-02T10:00:00Z"));
        Post second = new Post(2, new User(3), "older", "https://img2", Instant.parse("2026-01-01T10:00:00Z"));

        PostDTO firstDto = new PostDTO(1, 2, "alice", "latest", "https://img1", Instant.parse("2026-01-02T10:00:00Z"));
        PostDTO secondDto = new PostDTO(2, 3, "bob", "older", "https://img2", Instant.parse("2026-01-01T10:00:00Z"));

        when(postRepository.findFeedPosts(userId)).thenReturn(List.of(first, second));
        when(postDTOMapper.apply(first)).thenReturn(firstDto);
        when(postDTOMapper.apply(second)).thenReturn(secondDto);

        List<PostDTO> result = feedService.getFeed(userId);

        assertEquals(2, result.size());
        assertEquals(firstDto, result.get(0));
        assertEquals(secondDto, result.get(1));

        verify(postRepository).findFeedPosts(eq(userId));
        verify(postDTOMapper).apply(first);
        verify(postDTOMapper).apply(second);
    }

    @Test
    void getFeed_returnsEmptyListWhenNoPosts() {
        Integer userId = 7;

        when(postRepository.findFeedPosts(userId)).thenReturn(List.of());

        List<PostDTO> result = feedService.getFeed(userId);

        assertTrue(result.isEmpty());
        verify(postRepository).findFeedPosts(eq(userId));
        verifyNoInteractions(postDTOMapper);
    }
}
