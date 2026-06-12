package com.themove.service;

public interface LikeService {

    void likePost(Long postId);

    void unlikePost(Long postId);

    long countLikes(Long postId);
}