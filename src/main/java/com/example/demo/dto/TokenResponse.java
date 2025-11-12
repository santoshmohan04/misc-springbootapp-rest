package com.example.demo.dto;

import java.util.Date;

public class TokenResponse {
    private String token;
    private Date expiry;

    public TokenResponse() {
    }

    public TokenResponse(String token, Date expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }
}