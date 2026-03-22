package com.steph.user;

import com.steph.exceptions.UserException;
import com.steph.user.DTOs.UpdateUserDTO;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.DTOs.UserProfileDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileDTOMapper userProfileDTOMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_shouldReturnUserProfileFTO_whenUserExists(){
        Integer userId = 1;

        User user = new User();
        UserProfileDTO dto = new UserProfileDTO(1, "stephane", null, null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userProfileDTOMapper.apply(user)).thenReturn(dto);

        UserProfileDTO result = userService.getUserById(userId);

        assertEquals(dto, result);
        verify(userRepository).findById(userId);
        verify(userProfileDTOMapper).apply(user);
    }


    @Test
    void getUserById_shouldThrowException_whenUserDoesNotExist() {
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> {
           userService.getUserById(userId);
        });

        verify(userRepository).findById(userId);
        verifyNoInteractions(userProfileDTOMapper);
    }


    @Test
    void getAllUsers_shouldReturnListofUserProfileDTO_whenUsersExist() {

    User user1 = new User();
    User user2 = new User();
    UserProfileDTO dto1 = new UserProfileDTO(1, "steph", null, null);
    UserProfileDTO dto2 = new UserProfileDTO(2, "steph2", null, null);

    when(userRepository.findAll()).thenReturn(List.of(user1, user2));
    when(userProfileDTOMapper.apply(any(User.class))).thenReturn(dto1, dto2);


    List<UserProfileDTO> result = userService.getAllUsers();

    assertEquals(2, result.size());
    assertTrue(result.contains(dto1));
    assertTrue(result.contains(dto2));

    verify(userRepository).findAll();
    verify(userProfileDTOMapper, times(2)).apply(any(User.class));
    }

    @Test
    void getAllUsers_shouldReturnEmptyList_whenNoUsersExist() {

        when(userRepository.findAll()).thenReturn(List.of());

        List<UserProfileDTO> result = userService.getAllUsers();

        assertTrue(result.isEmpty());

        verify(userRepository).findAll();
        verifyNoInteractions(userProfileDTOMapper);
    }

    @Test
    void updateUser_shouldReturnUpdatedUserProfileDTO_whenUserExists() {
        // Inputs
        Integer userId = 1;
        Integer authenticatedUserId = 1;
        UpdateUserDTO updateDTO = new UpdateUserDTO("newName", "newBio", "newImage");

        // Existing user in repository
        User user = mock(User.class); // or new User(), but mocking works for updateFromDTO

        // DTO that mapper should return
        UserProfileDTO mappedDTO = new UserProfileDTO(1, "newName", "newBio", "newImage");

        // Stubs
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userProfileDTOMapper.apply(user)).thenReturn(mappedDTO);

        // Call the service method
        UserProfileDTO result = assertDoesNotThrow(() ->
                userService.updateUser(updateDTO, userId, authenticatedUserId)
        );

        // Assertions
        assertEquals(mappedDTO, result);

        // Verify interactions
        verify(userRepository).findById(userId);       // fetched user
        verify(user).updateFromDTO(updateDTO);         // update was applied
        verify(userRepository).save(user);             // saved user
        verify(userProfileDTOMapper).apply(user);      // mapped to DTO
    }

    @Test
    void updateUser_shouldThrowUserException_whenUserDoesNotExist() {

        Integer userId = 99;
        Integer authenticatedUserId = 99;
        UpdateUserDTO updateDTO = new UpdateUserDTO("name", "bio", "image");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () ->
                userService.updateUser(updateDTO, userId, authenticatedUserId)
        );

        assertEquals("99 not found", exception.getMessage());

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userProfileDTOMapper);
    }

    @Test
    void deleteUser_shouldCallRepository() {

        Integer userId = 1;
        Integer authenticatedUserId = 1;

        assertDoesNotThrow(() -> userService.deleteUser(userId, authenticatedUserId));

        verify(userRepository).deleteById(userId);
    }

    @Test
    void updateUser_shouldThrowAccessDeniedException_whenUserTriesToUpdateAnotherUser() {
        Integer userId = 1;
        Integer authenticatedUserId = 2;
        UpdateUserDTO updateDTO = new UpdateUserDTO("name", "bio", "image");

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                userService.updateUser(updateDTO, userId, authenticatedUserId)
        );

        assertEquals("You can only update your own profile", exception.getMessage());
        verifyNoInteractions(userRepository);
        verifyNoInteractions(userProfileDTOMapper);
    }

    @Test
    void deleteUser_shouldThrowAccessDeniedException_whenUserTriesToDeleteAnotherUser() {
        Integer userId = 1;
        Integer authenticatedUserId = 2;

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                userService.deleteUser(userId, authenticatedUserId)
        );

        assertEquals("You can only delete your own profile", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

}
