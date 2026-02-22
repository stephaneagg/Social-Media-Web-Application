package com.steph.user;

import com.steph.user.DTOs.UserProfileDTO;
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
