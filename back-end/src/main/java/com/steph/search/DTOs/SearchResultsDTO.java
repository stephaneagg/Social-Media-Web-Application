package com.steph.search.DTOs;

import com.steph.post.DTOs.PostDTO;
import com.steph.user.DTOs.UserProfileDTO;

import java.util.List;

public record SearchResultsDTO(
        List<UserProfileDTO> users,
        List<PostDTO> posts) {
}
