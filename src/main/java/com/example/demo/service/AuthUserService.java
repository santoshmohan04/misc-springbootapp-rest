package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.AuthUser;
import com.example.demo.repository.AuthUserRepository;
import com.example.demo.util.JwtUtil;

@Service
public class AuthUserService {

    private final AuthUserRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthUserService(AuthUserRepository repo) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthUser signup(String name, String email, String rawPassword, String profilepic) {
        if (repo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }
        String hash = passwordEncoder.encode(rawPassword);
        AuthUser u = new AuthUser(name, email, hash, profilepic);
        return repo.save(u);
    }

    public AuthUser login(String email, String rawPassword) {
        Optional<AuthUser> opt = repo.findByEmail(email);
        if (opt.isEmpty()) throw new IllegalArgumentException("Invalid credentials");
        AuthUser user = opt.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new IllegalArgumentException("Invalid credentials");
        }
        // remove password before returning? caller shouldn't send it to clients
        user.setPassword(null);
        return user;
    }

    public AuthUser updateUserDetails(String userId, String newName, String newEmail, String newProfilepic) {
        AuthUser user = repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (newEmail != null && !newEmail.equals(user.getEmail())) {
            if (repo.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("Email already in use");
            }
            user.setEmail(newEmail);
        }
        if (newName != null) user.setName(newName);
        user = repo.save(user);
        user.setPassword(null);
        return user;
    }

    public void changePassword(String userId, String currentPassword, String newPassword) {
        AuthUser user = repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        repo.save(user);
    }
}
