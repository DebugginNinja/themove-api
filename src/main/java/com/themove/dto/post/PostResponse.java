package com.themove.dto.post;

import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    private String username;
    private String email;

    public PostResponse(Long id, String content, String imageUrl,
                        LocalDateTime createdAt,
                        String username, String email) {
        this.id = id;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}