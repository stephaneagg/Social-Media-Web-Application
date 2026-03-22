package com.steph.follow;

import com.steph.exceptions.FollowException;
import com.steph.exceptions.UserException;
import com.steph.follows.Follow;
import com.steph.follows.FollowId;
import com.steph.follows.FollowRepository;
import com.steph.follows.FollowService;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.DTOs.UserProfileDTOMapper;
import com.steph.user.User;
import com.steph.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FollowServiceTest {

    private FollowRepository followRepository;
    private UserRepository userRepository;
    private UserProfileDTOMapper mapper;
    private FollowService followService;

    @BeforeEach
    void setUp() {
        followRepository = mock(FollowRepository.class);
        userRepository = mock(UserRepository.class);
        mapper = mock(UserProfileDTOMapper.class);
        followService = new FollowService(followRepository, userRepository, mapper);
    }

    @Test
    void getFollowers_returnsMappedUsers() {
        User follower = new User(1); follower.setDisplayName("Alice");
        Follow follow = new Follow(follower, new User(2));
        when(followRepository.findByFollowedId(2)).thenReturn(List.of(follow));
        UserProfileDTO dto = new UserProfileDTO(1, "Alice", null, null);
        when(mapper.apply(follower)).thenReturn(dto);

        List<UserProfileDTO> result = followService.getFollowers(2);

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).displayName());
    }

    @Test
    void getFollows_returnsMappedUsers() {
        User followed = new User(2); followed.setDisplayName("Bob");
        Follow follow = new Follow(new User(1), followed);
        when(followRepository.findByFollowerId(1)).thenReturn(List.of(follow));
        UserProfileDTO dto = new UserProfileDTO(2, "Bob", null, null);
        when(mapper.apply(followed)).thenReturn(dto);

        List<UserProfileDTO> result = followService.getFollows(1);

        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).displayName());
    }

    @Test
    void follow_createsFollow_whenValid() {
        User follower = new User(1);
        User followed = new User(2);
        when(userRepository.findById(1)).thenReturn(Optional.of(follower));
        when(userRepository.findById(2)).thenReturn(Optional.of(followed));
        when(followRepository.existsById(any(FollowId.class))).thenReturn(false);

        ResponseEntity<Void> response = followService.follow(2, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArgumentCaptor<Follow> captor = ArgumentCaptor.forClass(Follow.class);
        verify(followRepository).save(captor.capture());
        assertEquals(1, captor.getValue().getFollower().getId());
        assertEquals(2, captor.getValue().getFollowed().getId());
    }

    @Test
    void follow_throwsFollowException_whenFollowingSelf() {
        User user = new User(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        FollowException exception = assertThrows(FollowException.class, () -> followService.follow(1, 1));
        assertEquals("A user cant follow themselves", exception.getMessage());
    }

    @Test
    void follow_throwsFollowException_whenAlreadyFollowing() {
        User follower = new User(1);
        User followed = new User(2);
        when(userRepository.findById(1)).thenReturn(Optional.of(follower));
        when(userRepository.findById(2)).thenReturn(Optional.of(followed));
        when(followRepository.existsById(any(FollowId.class))).thenReturn(true);

        FollowException exception = assertThrows(FollowException.class, () -> followService.follow(2, 1));
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    void unfollow_deletesFollow_whenExists() {
        FollowId followId = new FollowId(1, 2);
        when(followRepository.existsById(followId)).thenReturn(true);

        ResponseEntity<Void> response = followService.unfollow(2, 1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(followRepository).deleteById(followId);
    }

    @Test
    void unfollow_throwsFollowException_whenNotExists() {
        FollowId followId = new FollowId(1, 2);
        when(followRepository.existsById(followId)).thenReturn(false);

        FollowException exception = assertThrows(FollowException.class, () -> followService.unfollow(2, 1));
        assertTrue(exception.getMessage().contains("not found"));
    }
}