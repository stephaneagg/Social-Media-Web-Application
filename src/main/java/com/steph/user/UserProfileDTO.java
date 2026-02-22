package com.steph.user;

// Object that gets passed to the front end to display on the user's profile
// Only contains attributes that are safe to expose to the front end
public record UserProfileDTO(
        Integer id,
        String displayName,
        String bio,
        String profileImageUrl
) {
}
