package com.themove.entity;

import com.themove.model.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // 🔥 NESTED COMMENTS (REPLIES)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    public Comment() {}

    public Comment(User user, Post post, String content, Comment parent) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.parent = parent;
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public Post getPost() { return post; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public Comment getParent() { return parent; }
    public List<Comment> getReplies() { return replies; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setPost(Post post) { this.post = post; }
    public void setContent(String content) { this.content = content; }
    public void setParent(Comment parent) { this.parent = parent; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}