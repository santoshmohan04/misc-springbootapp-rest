package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AuthUserService;
import com.example.demo.dto.*;
import com.example.demo.model.AuthUser;
import com.example.demo.util.JwtUtil;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUserService service;
    private final JwtUtil jwtUtil;

    public AuthController(AuthUserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- SIGNUP ----------------
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Validated SignupRequest req) {
        try {
            AuthUser user = service.signup(req.getName(), req.getEmail(), req.getPassword(), req.getProfilepic());
            String token = jwtUtil.generateToken(user.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            AuthResponse resp = new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfilepic(),
                token,
                refreshToken,
                jwtUtil.extractExpiration(token)
            );
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest req) {
        try {
            AuthUser user = service.login(req.getEmail(), req.getPassword());
            String token = jwtUtil.generateToken(user.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            AuthResponse resp = new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfilepic(),
                token,
                refreshToken,
                jwtUtil.extractExpiration(token)
            );
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // ---------------- REFRESH TOKEN ----------------
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header missing");
            }

            String oldToken = header.replace("Bearer ", "");
            String email = jwtUtil.extractUsername(oldToken);

            if (jwtUtil.validateToken(oldToken, email)) {
                String newToken = jwtUtil.generateToken(email);
                return ResponseEntity.ok(new TokenResponse(newToken, jwtUtil.extractExpiration(newToken)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // ---------------- UPDATE USER DETAILS ----------------
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            HttpServletRequest request,
            @RequestBody @Validated UpdateUserRequest req) {

        try {
            String token = extractToken(request);
            String email = jwtUtil.extractUsername(token);

            if (!jwtUtil.validateToken(token, email)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired or invalid");
            }

            AuthUser user = service.updateUserDetails(req.getUserId(), req.getName(), req.getEmail(), req.getProfilepic());
            return ResponseEntity.ok(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getProfilepic()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ---------------- CHANGE PASSWORD ----------------
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            HttpServletRequest request,
            @RequestBody @Validated ChangePasswordRequest req) {

        try {
            String token = extractToken(request);
            String email = jwtUtil.extractUsername(token);

            if (!jwtUtil.validateToken(token, email)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired or invalid");
            }

            service.changePassword(req.getUserId(), req.getCurrentPassword(), req.getNewPassword());
            return ResponseEntity.ok("Password changed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }
        return header.substring(7);
    }
}