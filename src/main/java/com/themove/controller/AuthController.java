package com.themove.controller;

import com.themove.dto.auth.LoginRequest;
import com.themove.dto.auth.RegisterRequest;
import com.themove.dto.response.AuthResponse;
import com.themove.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

   @PostMapping("/login")
public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    String token = authService.login(request);
    return ResponseEntity.ok(new AuthResponse(token));
} 
    
    
    
}