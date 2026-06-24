package com.steph.follows;

import com.steph.exceptions.FollowException;
import com.steph.exceptions.UserException;
import com.steph.follows.DTOs.FollowSuggestionDTO;
import com.steph.follows.DTOs.RecentFollowerDTO;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("follows")
@Validated
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService){
        this.followService = followService;
    }

    // Gets a list of followers of a user
    // Who follows this user?
    @GetMapping("followers/{userId}")
    public List<UserProfileDTO> getFollowers(@PathVariable Integer userId) {
        return followService.getFollowers(userId);
    }

    // Gets a list of users a user is following
    // Who does this user follow?
    @GetMapping("following/{userId}")
    public List<UserProfileDTO> getFollows(@PathVariable Integer userId){
        return followService.getFollows(userId);
    }

    @GetMapping("suggestions")
    public List<FollowSuggestionDTO> getSuggestions(
            @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId,
            @RequestParam(defaultValue = "5") @Min(1) @Max(50) Integer limit) {
        return followService.getFollowSuggestions(authenticatedUserId, limit);
    }

    @GetMapping("recent")
    public List<RecentFollowerDTO> getRecentFollowers(
            @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId
    ) {
        return followService.getRecentFollowers(authenticatedUserId);
    }
    // Create a follow relationship
    // Follow a User
    @PostMapping("{followedId}")
    public ResponseEntity<Void> follow(@PathVariable Integer followedId,
                                 @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws UserException {
        return followService.follow(followedId, authenticatedUserId);
    }

    // Delete a follow relationship
    // Unfollow a user
    @DeleteMapping("{followedId}")
    public ResponseEntity<Void> unfollow(@PathVariable Integer followedId,
                                         @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws FollowException {
        return followService.unfollow(followedId, authenticatedUserId);
    }


}
