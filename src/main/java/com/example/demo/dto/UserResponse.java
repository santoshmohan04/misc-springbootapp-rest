package com.example.demo.dto;

public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String profilepic;

    public UserResponse(String id, String name, String email, String profilepic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilepic = profilepic;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getProfilepic() { return profilepic; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setProfilepic(String profilepic) { this.profilepic = profilepic; }
}
