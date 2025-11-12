package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private String profilepic;

    // ---------- Constructors ----------
    public UpdateUserRequest() {}

    public UpdateUserRequest(String userId, String name, String email, String profilepic) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profilepic = profilepic;
    }

    // ---------- Getters & Setters ----------
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}