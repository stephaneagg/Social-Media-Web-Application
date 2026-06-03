package com.steph.user.DTOs;

import com.steph.upload.ImageUrlNormalizer;
import com.steph.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CurrentUserDTOMapper implements Function<User, CurrentUserDTO> {

    private final ImageUrlNormalizer imageUrlNormalizer;

    public CurrentUserDTOMapper(ImageUrlNormalizer imageUrlNormalizer) {
        this.imageUrlNormalizer = imageUrlNormalizer;
    }

    @Override
    public CurrentUserDTO apply(User user) {
        return new CurrentUserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                imageUrlNormalizer.normalize(user.getProfileImageUrl()),
                imageUrlNormalizer.normalize(user.getCoverImageUrl())
        );
    }
}
