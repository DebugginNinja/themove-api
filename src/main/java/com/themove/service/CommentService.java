package com.themove.service;

import com.themove.dto.comment.CommentRequest;
import com.themove.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse addComment(Long postId, CommentRequest request);

    List<CommentResponse> getCommentsByPost(Long postId);

    void deleteComment(Long commentId);
}