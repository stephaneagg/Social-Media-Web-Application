package com.steph.follows;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService){
        this.followService = followService;
    }

    // Gets a list of followers of a user
    @GetMapping("followers/{id}")
    public void getFollowersOf(@PathVariable Integer id) {

    }

    // Gets a list of users a user is following
    @GetMapping("following/{id}")
    public void getFollowsOf(@PathVariable Integer id){

    }

    @PostMapping("")
    public void follow() {

    }


}
