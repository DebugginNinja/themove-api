package com.themove.controller;

import com.themove.dto.comment.CommentRequest;
import com.themove.dto.comment.CommentResponse;
import com.themove.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request) {

        return ResponseEntity.ok(
                commentService.addComment(postId, request)
        );
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {

        return ResponseEntity.ok(
                commentService.getCommentsByPost(postId)
        );
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok("Comment deleted");
    }
}