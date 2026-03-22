package com.steph.feed;


import com.steph.post.DTOs.PostDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public List<PostDTO> getFeed(@AuthenticationPrincipal(expression = "id") Integer authenticatedUserId) {
        return feedService.getFeed(authenticatedUserId);
    }

}
