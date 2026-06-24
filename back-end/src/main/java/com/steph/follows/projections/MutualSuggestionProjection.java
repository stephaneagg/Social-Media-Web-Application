package com.steph.follows.projections;

public interface MutualSuggestionProjection {
    Integer getUserId();
    String getDisplayName();
    String getProfileImageUrl();
    Long getMutualCount();

}
