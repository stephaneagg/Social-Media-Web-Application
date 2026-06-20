package com.steph.search;


import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.PostDTOMapper;
import com.steph.post.PostRepository;
import com.steph.search.DTOs.SearchResultsDTO;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.DTOs.UserProfileDTOMapper;
import com.steph.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {


    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserProfileDTOMapper userProfileDTOMapper;
    private final PostDTOMapper postDTOMapper;

    public SearchService(
            UserRepository userRepository,
            PostRepository postRepository,
            UserProfileDTOMapper userProfileDTOMapper,
            PostDTOMapper postDTOMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userProfileDTOMapper = userProfileDTOMapper;
        this.postDTOMapper = postDTOMapper;
    }


    public SearchResultsDTO search(String query) {
        List<UserProfileDTO> users = userRepository.findByDisplayNameContainingIgnoreCase(query)
                .stream()
                .map(userProfileDTOMapper)
                .toList();

        List<PostDTO> posts = postRepository.findByContentTextContainingIgnoreCase(query)
                .stream()
                .map(postDTOMapper)
                .toList();

        return new SearchResultsDTO(users, posts);
    }

}
