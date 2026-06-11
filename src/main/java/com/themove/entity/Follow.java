package com.themove.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private com.themove.model.User follower;

    @ManyToOne
    private com.themove.model.User following;

    public Follow() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.themove.model.User getFollower() {
        return follower;
    }

    public void setFollower(com.themove.model.User follower) {
        this.follower = follower;
    }

    public com.themove.model.User getFollowing() {
        return following;
    }

    public void setFollowing(com.themove.model.User following) {
        this.following = following;
    }
}