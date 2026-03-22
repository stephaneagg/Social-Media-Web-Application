package com.steph.user.DTOs;

import com.steph.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserProfileDTOMapper implements Function<User, UserProfileDTO> {
    @Override
    public UserProfileDTO apply(User user) {
        return new UserProfileDTO(
                user.getId(),
                user.getDisplayName(),
                user.getBio(),
                user.getProfileImageUrl()
        );
    }
}
