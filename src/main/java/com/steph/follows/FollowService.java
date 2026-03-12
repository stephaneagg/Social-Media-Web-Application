package com.steph.follows;

import com.steph.exceptions.FollowException;
import com.steph.exceptions.UserException;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.DTOs.UserProfileDTOMapper;
import com.steph.user.User;
import com.steph.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {


    FollowRepository followRepository;
    UserRepository userRepository;
    UserProfileDTOMapper userProfileDTOMapper;

    public FollowService(FollowRepository followRepository,
                         UserRepository userRepository,
                         UserProfileDTOMapper userProfileDTOMapper) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.userProfileDTOMapper = userProfileDTOMapper;
    }

    // find relationships in follows where followed is userId
    public List<UserProfileDTO> getFollowers(Integer userId) {
        return followRepository.findByFollowedId(userId)
                .stream()
                .map(Follow::getFollower)
                .map(userProfileDTOMapper)
                .toList();
    }

    // find relationsips in follows where follower is userId
    public List<UserProfileDTO> getFollows(Integer userId) {
        return followRepository.findByFollowerId(userId)
                .stream()
                .map(Follow::getFollowed)
                .map(userProfileDTOMapper)
                .toList();
    }

    /*
     check for illigal edge cases
        cant follow yourself
        cant follow someone you already follow
     */
    public ResponseEntity<Void> follow(Integer followedId, Integer followerId) {

        Follow follow = new Follow();
        FollowId followId = new FollowId(followerId, followedId);
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new UserException("User Id: " + followedId + " not found"));
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserException("User Id: " + followerId + " not found"));

        follow.setId(followId);
        follow.setFollowed(followed);
        follow.setFollower(follower);

        // Can't follow yourself
        if (followed.equals(follower)) {
            throw new FollowException("A user cant follow themselves");
        }

        if (followRepository.existsById(followId)) {
            throw new FollowException("Follow relationship: " + followId +  " already exists");
        }

        followRepository.save(follow);

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<Void> unfollow(Integer followedId, Integer followerId) {

        FollowId followId = new FollowId(followerId, followedId);

        if (!followRepository.existsById(followId)) {
            throw new FollowException("Follow relationship: " + followId + " not found");
        }

        followRepository.deleteById(followId);
        return ResponseEntity.noContent().build();
    }
}
