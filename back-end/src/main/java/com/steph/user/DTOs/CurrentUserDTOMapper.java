package com.steph.user.DTOs;

import com.steph.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CurrentUserDTOMapper implements Function<User, CurrentUserDTO> {
    @Override
    public CurrentUserDTO apply(User user) {
        return new CurrentUserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getProfileImageUrl()
        );
    }
}
