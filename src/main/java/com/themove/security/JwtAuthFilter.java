package com.themove.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("\n=== JWT FILTER START ===");
        System.out.println("Request URI: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("NO BEARER TOKEN FOUND");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("TOKEN RECEIVED: " + token);

        try {
            String email = jwtService.extractEmail(token);
            System.out.println("EXTRACTED EMAIL: " + email);

            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                new ArrayList<>() // no roles for now
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("AUTH SET IN SECURITY CONTEXT");
            }

        } catch (Exception e) {
            System.out.println("JWT VALIDATION FAILED: " + e.getMessage());
            e.printStackTrace();

            // IMPORTANT: do NOT crash request, just continue unauthenticated
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}