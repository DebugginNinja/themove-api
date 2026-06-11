package com.themove.service;

import com.themove.dto.post.PostRequest;

public interface PostService {

    Object createPost(PostRequest request);

    Object getPostById(Long id);
}