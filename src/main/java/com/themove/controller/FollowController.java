package com.themove.controller;

import com.themove.dto.response.ApiResponse;
import com.themove.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> follow(@PathVariable Long userId) {

        followService.followUser(userId);

        return ResponseEntity.ok(
                ApiResponse.success("Followed user", null)
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> unfollow(@PathVariable Long userId) {

        followService.unfollowUser(userId);

        return ResponseEntity.ok(
                ApiResponse.success("Unfollowed user", null)
        );
    }
}