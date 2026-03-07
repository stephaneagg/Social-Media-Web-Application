package com.steph.user;

import com.steph.exceptions.UserException;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.DTOs.UserProfileDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void GetUserById_shouldThrowException_whenUserDoesNotExist() {
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> {
           userService.getUserById(userId);
        });

        verify(userRepository).findById(userId);
        verifyNoInteractions(userProfileDTOMapper);
    }


}
