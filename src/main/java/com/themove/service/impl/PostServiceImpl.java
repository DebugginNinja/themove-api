package com.themove.service.impl;

import com.themove.dto.post.PostRequest;
import com.themove.dto.post.PostResponse;
import com.themove.entity.Post;
import com.themove.model.User;
import com.themove.repository.PostRepository;
import com.themove.service.AuthService;
import com.themove.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AuthService authService;

    public PostServiceImpl(PostRepository postRepository,
                           AuthService authService) {
        this.postRepository = postRepository;
        this.authService = authService;
    }

    @Override
    public PostResponse createPost(PostRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = authService.getUserByEmail(email);

        Post post = new Post();
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setCreatedAt(LocalDateTime.now());

        // REAL OWNERSHIP (NO MORE SYSTEM DATA)
        post.setUser(user);

        Post saved = postRepository.save(post);

        return mapToResponse(saved);
    }

    @Override
    public PostResponse getPostById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return mapToResponse(post);
    }

    private PostResponse mapToResponse(Post post) {

        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getUser().getUsername(),
                post.getUser().getEmail()
        );
    }
}