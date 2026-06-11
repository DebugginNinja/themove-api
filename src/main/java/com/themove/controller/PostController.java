package com.themove.controller;

import com.themove.dto.post.PostRequest;
import com.themove.dto.response.ApiResponse;
import com.themove.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createPost(@RequestBody PostRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Post created successfully",
                        postService.createPost(request)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getPost(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Post retrieved successfully",
                        postService.getPostById(id)
                )
        );
    }
}