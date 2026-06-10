package com.themove.controller;

import com.themove.dto.auth.UpdateUserRequest;
import com.themove.dto.response.UserResponse;
import com.themove.model.User;
import com.themove.service.AuthService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    // GET current user
    @GetMapping("/me")
    public UserResponse me() {
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = authService.getUserByEmail(email);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getLocation()
        );
    }

    // UPDATE current user
    @PutMapping("/me")
    public UserResponse update(@RequestBody UpdateUserRequest request) {

        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = authService.getUserByEmail(email);

        if (request.getUsername() != null)
            user.setUsername(request.getUsername());

        if (request.getBio() != null)
            user.setBio(request.getBio());

        if (request.getLocation() != null)
            user.setLocation(request.getLocation());

        authService.save(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getLocation()
        );
    }
}