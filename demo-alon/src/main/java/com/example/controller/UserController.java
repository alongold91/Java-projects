package com.example.controller;

import java.util.List;

import com.example.dto.CreateUserRequest;
import com.example.dto.ErrorResponse;
import com.example.dto.UpdateUserRequest;
import com.example.dto.UserResponse;
import com.example.service.UserService;
import com.example.util.ErrorUtils;
import com.example.util.ValidationUtils;

import io.javalin.http.Context;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void getAllUsers(Context ctx) {
        try {
            ctx.json(userService.getAllUsers());
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void getUserById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("userId"));
            userService.getUserById(id).ifPresentOrElse(ctx::json,
                    () -> ctx.status(404).json(new ErrorResponse(List.of("User not found"))));
        } catch (NumberFormatException e) {
            ctx.status(400).json(new ErrorResponse(List.of("Invalid user ID")));
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void createUser(Context ctx) {
        try {
            CreateUserRequest request = ctx.bodyAsClass(CreateUserRequest.class);
            ValidationUtils.validateAndGetErrors(request).ifPresentOrElse(
                    error -> ctx.status(400).json(new ErrorResponse(error)),
                    () -> {
                        UserResponse response = userService.createUser(request);
                        ctx.status(201).json(response);
                    });

        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void updateUser(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("userId"));
            UpdateUserRequest request = ctx.bodyAsClass(UpdateUserRequest.class);

            ValidationUtils.validateAndGetErrors(request).ifPresentOrElse(
                    error -> ctx.status(400).json(new ErrorResponse(error)),
                    () -> {
                        userService.updateUser(id, request).ifPresentOrElse(res -> ctx.status(201).json(res),
                                () -> ctx.status(404).json(new ErrorResponse(List.of("User not found"))));
                    });
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void deleteUser(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("userId"));
            if (userService.deleteUser(id)) {
                ctx.status(204).json("User Deleted");
            } else {
                ctx.status(404).json(new ErrorResponse(List.of("User not found")));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new ErrorResponse(List.of("Invalid user ID")));
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }
}
