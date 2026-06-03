package com.steph.user.DTOs;

import com.steph.upload.ImageUrlNormalizer;
import com.steph.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserProfileDTOMapper implements Function<User, UserProfileDTO> {

    private final ImageUrlNormalizer imageUrlNormalizer;

    public UserProfileDTOMapper(ImageUrlNormalizer imageUrlNormalizer) {
        this.imageUrlNormalizer = imageUrlNormalizer;
    }

    @Override
    public UserProfileDTO apply(User user) {
        return new UserProfileDTO(
                user.getId(),
                user.getDisplayName(),
                user.getBio(),
                imageUrlNormalizer.normalize(user.getProfileImageUrl()),
                imageUrlNormalizer.normalize(user.getCoverImageUrl())
        );
    }
}
