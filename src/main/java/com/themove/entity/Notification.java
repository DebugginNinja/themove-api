package com.themove.entity;

import com.themove.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who receives the notification
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    // Who triggered it
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false)
    private String type; 
    // LIKE, COMMENT, FOLLOW

    private Long postId; // optional (for LIKE/COMMENT)

    private Long commentId; // optional (for COMMENT replies)

    private boolean isRead = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {}

    public Notification(User recipient,
                        User sender,
                        String type,
                        Long postId,
                        Long commentId) {
        this.recipient = recipient;
        this.sender = sender;
        this.type = type;
        this.postId = postId;
        this.commentId = commentId;
    }

    public Long getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}