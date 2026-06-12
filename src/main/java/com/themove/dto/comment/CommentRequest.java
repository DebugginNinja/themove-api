package com.themove.dto.comment;

public class CommentRequest {

    private String content;
    private Long parentId; // 🔥 NEW (for replies)

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}