// CreateUserRequest.java
package com.interview.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.interview.validation.UniqueEmail;

public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @UniqueEmail(message = "Email already exists")
    private String email;
    
    // Constructors
    public CreateUserRequest() {}
    
    public CreateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}