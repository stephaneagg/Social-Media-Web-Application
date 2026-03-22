package com.steph.feed;

import com.steph.post.DTOs.PostDTO;
import com.steph.post.DTOs.PostDTOMapper;
import com.steph.post.Post;
import com.steph.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    private final PostRepository postRepository;
    private final PostDTOMapper postDTOMapper;

    public FeedService(PostRepository postRepository, PostDTOMapper postDTOMapper) {
        this.postRepository = postRepository;
        this.postDTOMapper = postDTOMapper;
    }

    // Method should return a list of posts belonging to users the authenticated user follows
    // The list should be ordered by date
    public List<PostDTO> getFeed(Integer userId) {

        List<Post> posts = postRepository.findFeedPosts(userId);

        return posts.stream()
                .map(postDTOMapper)
                .toList();

    }
}
