package com.themove.service.impl;

import com.themove.dto.post.PostRequest;
import com.themove.dto.post.PostResponse;
import com.themove.entity.Post;
import com.themove.model.User;
import com.themove.repository.PostRepository;
import com.themove.repository.UserRepository;
import com.themove.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostResponse createPost(PostRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized request");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setCreatedAt(LocalDateTime.now());

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

    @Override
    public Page<PostResponse> getFeed(Pageable pageable) {

        return postRepository
                .findAllByOrderByCreatedAtDesc(pageable)
                .map(this::mapToResponse);
    }

    private PostResponse mapToResponse(Post post) {

        User user = post.getUser();

        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                user != null ? user.getUsername() : "unknown",
                user != null ? user.getEmail() : "unknown"
        );
    }
}