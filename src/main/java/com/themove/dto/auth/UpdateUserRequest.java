package com.themove.dto.auth;

public class UpdateUserRequest {
    private String username;
    private String bio;
    private String location;

    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public String getLocation() { return location; }

    public void setUsername(String username) { this.username = username; }
    public void setBio(String bio) { this.bio = bio; }
    public void setLocation(String location) { this.location = location; }
}