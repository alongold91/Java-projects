package com.example.routes;

import com.example.controller.UserController;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;

public class UserRoutes {
    private final UserController userController;
    private final TodoRoutes todoRoutes;

    public UserRoutes(UserController userController, TodoRoutes todoRoutes) {
        this.userController = userController;
        this.todoRoutes = todoRoutes;
    }

    public void register(Javalin app) {
        app.routes(() -> {
            ApiBuilder.path("users", () -> {
                ApiBuilder.get(userController::getAllUsers);
                ApiBuilder.path("create", () -> ApiBuilder.post(userController::createUser));
                ApiBuilder.path("{userId}", () -> {
                    ApiBuilder.get(userController::getUserById);
                    ApiBuilder.patch(userController::updateUser);
                    ApiBuilder.delete(userController::deleteUser);
                    todoRoutes.registerNestedRoutes();
                });
            });
        });
    }
}
