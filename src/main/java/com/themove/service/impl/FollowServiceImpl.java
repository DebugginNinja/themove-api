package com.themove.service.impl;

import com.themove.entity.Follow;
import com.themove.model.User;
import com.themove.repository.FollowRepository;
import com.themove.repository.UserRepository;
import com.themove.service.FollowService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowServiceImpl(FollowRepository followRepository,
                             UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void followUser(Long targetUserId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        if (followRepository.existsByFollowerAndFollowing(currentUser, targetUser)) {
            return;
        }

        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowing(targetUser);

        followRepository.save(follow);
    }

    @Override
    public void unfollowUser(Long targetUserId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        followRepository.findByFollower(currentUser).stream()
                .filter(f -> f.getFollowing().getId().equals(targetUserId))
                .findFirst()
                .ifPresent(followRepository::delete);
    }
}