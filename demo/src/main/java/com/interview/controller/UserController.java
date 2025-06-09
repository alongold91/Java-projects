// UserController.java
package com.interview.controller;

import com.interview.service.UserService;
import com.interview.dto.CreateUserRequest;
import io.javalin.http.Context;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import java.util.Set;
import java.util.stream.Collectors;

public class UserController {
    private final UserService userService;
    private final Validator validator;
    
    public UserController(UserService userService) {
        this.userService = userService;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }
    
    public void getAllUsers(Context ctx) {
        ctx.json(userService.getAllUsers());
    }
    
    public void getUserById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            userService.getUserById(id)
                .ifPresentOrElse(
                    ctx::json,
                    () -> ctx.status(404).json(new ErrorResponse("User not found"))
                );
        } catch (NumberFormatException e) {
            ctx.status(400).json(new ErrorResponse("Invalid user ID"));
        }
    }
    
    public void createUser(Context ctx) {
        try {
            CreateUserRequest request = ctx.bodyAsClass(CreateUserRequest.class);
            
            // Validate request
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            if (!violations.isEmpty()) {
                String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
                ctx.status(400).json(new ErrorResponse(errorMessage));
                return;
            }
            
            var response = userService.createUser(request);
            ctx.status(201).json(response);
        } catch (RuntimeException e) {
            ctx.status(400).json(new ErrorResponse(e.getMessage()));
        }
    }
    
    public void updateUser(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            CreateUserRequest request = ctx.bodyAsClass(CreateUserRequest.class);
            
            // Validate request
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            if (!violations.isEmpty()) {
                String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
                ctx.status(400).json(new ErrorResponse(errorMessage));
                return;
            }
            
            userService.updateUser(id, request)
                .ifPresentOrElse(
                    ctx::json,
                    () -> ctx.status(404).json(new ErrorResponse("User not found"))
                );
        } catch (NumberFormatException e) {
            ctx.status(400).json(new ErrorResponse("Invalid user ID"));
        }
    }
    
    public void deleteUser(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            if (userService.deleteUser(id)) {
                ctx.status(204);
            } else {
                ctx.status(404).json(new ErrorResponse("User not found"));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new ErrorResponse("Invalid user ID"));
        }
    }
    
    static class ErrorResponse {
        public String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}