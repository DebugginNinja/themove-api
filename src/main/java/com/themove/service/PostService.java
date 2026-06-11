package com.themove.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.themove.dto.post.PostRequest;
import com.themove.dto.post.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest request);

    PostResponse getPostById(Long id);



Page<PostResponse> getFeed(Pageable pageable);
}