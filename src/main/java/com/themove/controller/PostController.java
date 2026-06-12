package com.themove.controller;


import com.themove.dto.post.PostRequest;
import com.themove.dto.response.ApiResponse;
import com.themove.service.LikeService;
import com.themove.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

        public PostController(PostService postService,
                                  LikeService likeService) {
                this.postService = postService;
                this.likeService = likeService;
        }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createPost(@RequestBody PostRequest request) {

        return ResponseEntity.status(201).body(
                ApiResponse.success(
                        "Post created successfully",
                        postService.createPost(request)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getPost(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Post retrieved successfully",
                        postService.getPostById(id)
                )
        );
    }

    // 🔥 STEP 8.7 — PAGINATED FEED
    @GetMapping("/feed")
    public ResponseEntity<ApiResponse<?>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<?> feed = postService.getFeed(PageRequest.of(page, size));

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Feed retrieved successfully",
                        feed
                )
        );
    }

    @PostMapping("/{postId}/like")
public ResponseEntity<String> likePost(@PathVariable Long postId) {
    likeService.likePost(postId);
    return ResponseEntity.ok("Post liked");
}

        @DeleteMapping("/{postId}/like")
        public ResponseEntity<String> unlikePost(@PathVariable Long postId) {
                likeService.unlikePost(postId);
                return ResponseEntity.ok("Post unliked");
        }
        
        @GetMapping("/{postId}/likes")
        public ResponseEntity<Long> countLikes(@PathVariable Long postId) {
                long likeCount = likeService.countLikes(postId);
                return ResponseEntity.ok(likeCount);
        }       

}