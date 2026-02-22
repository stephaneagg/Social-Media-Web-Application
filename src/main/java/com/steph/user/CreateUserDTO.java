package com.steph.user;

public record CreateUserDTO(
        String username,
        String email,
        String password,
        String displayName,
        String bio,
        String profileImageUrl
) {
}
