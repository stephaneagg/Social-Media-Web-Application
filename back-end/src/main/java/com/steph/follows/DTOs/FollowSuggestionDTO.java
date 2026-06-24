package com.steph.follows.DTOs;

public record FollowSuggestionDTO (
        Integer userId,
        String displayName,
        String profileImageUrl,
        Long mutualCount,
        SuggestionReason reason
) {
    public enum SuggestionReason {MUTUAL, POPULAR}
}
