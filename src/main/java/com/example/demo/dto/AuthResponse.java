package com.example.demo.dto;

import java.util.Date;

public class AuthResponse {

    private String id;
    private String name;
    private String email;
    private String profilepic;
    private String token;
    private String refreshToken;
    private Date expiry;

    public AuthResponse() {
    }

    public AuthResponse(String id, String name, String email, String profilepic, String token, String refreshToken, Date expiry) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilepic = profilepic;
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiry = expiry;
    }

    // ---------- Getters and Setters ----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    // ---------- toString (optional for logging) ----------
    @Override
    public String toString() {
        return "AuthResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilepic='" + profilepic + '\'' +
                ", token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiry=" + expiry +
                '}';
    }
}