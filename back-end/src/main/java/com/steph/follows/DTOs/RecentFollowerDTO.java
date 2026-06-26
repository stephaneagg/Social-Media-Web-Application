package com.steph.follows.DTOs;

import java.time.Instant;

public record RecentFollowerDTO(
        Integer userId,
        String displayName,
        String profileImageUrl,
        Instant createdAt
) {
}
