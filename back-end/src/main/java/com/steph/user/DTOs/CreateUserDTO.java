package com.steph.user.DTOs;

public record CreateUserDTO(
        String username,
        String email,
        String password,
        String displayName,
        String bio,
        String profileImageUrl
) {
}
