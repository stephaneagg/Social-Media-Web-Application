package com.steph.user.DTOs;

public record CurrentUserDTO (
    Integer id,
    String username,
    String email,
    String displayName,
    String profileImageUrl
) {
}
