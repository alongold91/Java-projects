// User.java
package com.interview.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    public User(Long id, String name, String email) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}