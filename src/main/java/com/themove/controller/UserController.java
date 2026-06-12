package com.themove.controller;

import com.themove.dto.UpdateProfileRequest;
import com.themove.dto.UserProfileDto;
import com.themove.mapper.UserMapper;
import com.themove.model.User;
import com.themove.repository.UserRepository;
import com.themove.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public UserController(AuthService authService,
        UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    // GET CURRENT USER PROFILE
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMe() {

        User user = authService.getCurrentUser();

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    // GET USER BY ID (public profile)
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserById(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    // UPDATE PROFILE
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(
            @RequestBody UpdateProfileRequest request) {

        User user = authService.getCurrentUser();

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getDisplayName() != null) {
            user.setDisplayName(request.getDisplayName());
        }

        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }

        if (request.getProfileImageUrl() != null) {
            user.setProfileImageUrl(request.getProfileImageUrl());
        }

        userRepository.save(user);

        return ResponseEntity.ok(UserMapper.toDto(user));
    }
}