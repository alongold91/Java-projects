// UserRoutes.java
package com.interview.routes;

import com.interview.controller.UserController;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;

public class UserRoutes {
    private final UserController userController;
    
    public UserRoutes(UserController userController) {
        this.userController = userController;
    }
    
    public void register(Javalin app) {
        app.routes(() -> {
            ApiBuilder.path("users", () -> {
                ApiBuilder.get(userController::getAllUsers);           // GET /users
                ApiBuilder.post(userController::createUser);          // POST /users
                ApiBuilder.path("{id}", () -> {
                    ApiBuilder.get(userController::getUserById);      // GET /users/{id}
                    ApiBuilder.put(userController::updateUser);       // PUT /users/{id}
                    ApiBuilder.delete(userController::deleteUser);    // DELETE /users/{id}
                });
            });
        });
    }
}