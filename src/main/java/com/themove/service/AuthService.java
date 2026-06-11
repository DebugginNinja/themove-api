package com.themove.service;
import com.themove.model.User;
import com.themove.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.themove.dto.auth.LoginRequest;
import com.themove.dto.auth.RegisterRequest;
import com.themove.model.User;
import com.themove.repository.UserRepository;
import com.themove.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // REGISTER
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }

    // LOGIN → RETURNS JWT TOKEN
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user.getEmail());
    }
    public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
}

    public void save(User user) {
        userRepository.save(user);
    }   
}