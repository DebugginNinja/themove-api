package com.themove.service;

public interface FollowService {

    void followUser(Long targetUserId);

    void unfollowUser(Long targetUserId);
}