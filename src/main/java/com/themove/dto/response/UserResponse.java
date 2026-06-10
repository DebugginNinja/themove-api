package com.themove.dto.response;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String bio;
    private String location;

    public UserResponse(Long id,
                        String username,
                        String email,
                        String bio,
                        String location) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}