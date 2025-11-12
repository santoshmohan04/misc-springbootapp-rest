package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "authusers")
public class AuthUser {
    @Id
    private String id;

    private String name;
    @Field("email")
    private String email;
    private String password; // store hashed password
    private String profilepic;

    // additional fields as needed
    public AuthUser() {}

    public AuthUser(String name, String email, String password, String profilepic) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilepic = profilepic;
    }

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfilepic() { return profilepic; }
    public void setProfilepic(String profilepic) { this.profilepic = profilepic; }
}