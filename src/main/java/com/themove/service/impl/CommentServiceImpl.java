package com.themove.service.impl;

import com.themove.dto.comment.CommentRequest;
import com.themove.dto.comment.CommentResponse;
import com.themove.entity.Comment;
import com.themove.entity.Post;
import com.themove.model.User;
import com.themove.repository.CommentRepository;
import com.themove.repository.PostRepository;
import com.themove.repository.UserRepository;
import com.themove.service.CommentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentResponse addComment(Long postId, CommentRequest request) {

        User user = getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment(
                user,
                post,
                request.getContent(),
                null
        );

        Comment saved = commentRepository.save(comment);

        return mapToResponse(saved);
    }

    @Override
    public List<CommentResponse> getCommentsByPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);
    }

    private CommentResponse mapToResponse(Comment comment) {

        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                List.of() // replies (empty for now)
        );
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}