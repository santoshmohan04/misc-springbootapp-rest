package com.example.demo.config;

import com.example.demo.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;

    // URLs that don't require authentication
    private final List<String> excludedUrls = List.of(
            "/api/auth/login",
            "/api/auth/signup",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-ui.html"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        // Skip JWT validation for public routes
        if (excludedUrls.stream().anyMatch(path::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header");
        }

        String token = header.substring(7);
        jwtUtil.validateToken(token);

        // ✅ Extract email and attach it to the request
        String email = jwtUtil.extractUsername(token);
        req.setAttribute("userEmail", email);

        chain.doFilter(request, response);
    }
}