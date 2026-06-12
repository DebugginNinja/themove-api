package com.themove.dto.comment;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {

    private Long id;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createdAt;

    private List<CommentResponse> replies; // 🔥 nested output

    public CommentResponse(Long id,
                           Long userId,
                           String username,
                           String content,
                           LocalDateTime createdAt,
                           List<CommentResponse> replies) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.replies = replies;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<CommentResponse> getReplies() { return replies; }
}