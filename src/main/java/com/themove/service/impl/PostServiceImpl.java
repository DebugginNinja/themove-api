package com.themove.service.impl;

import com.themove.dto.post.PostRequest;
import com.themove.dto.post.PostResponse;
import com.themove.entity.Post;
import com.themove.repository.PostRepository;
import com.themove.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostResponse createPost(PostRequest request) {

        Post post = new Post();
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setCreatedAt(LocalDateTime.now());

        Post saved = postRepository.save(post);

        return mapToResponse(saved);
    }

    @Override
    public PostResponse getPostById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return mapToResponse(post);
    }

    @Override
    public Page<PostResponse> getFeed(Pageable pageable) {

        return postRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    private PostResponse mapToResponse(Post post) {

        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getUser() != null ? post.getUser().getUsername() : null,
                post.getUser() != null ? post.getUser().getDisplayName() : null
        );
    }
}