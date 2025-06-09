package com.example.routes;

import com.example.controller.TodoController;

import io.javalin.apibuilder.ApiBuilder;

public class TodoRoutes {
    private final TodoController todoController;

    public TodoRoutes(TodoController todoController) {
        this.todoController = todoController;
    }

    public void registerNestedRoutes() {
        ApiBuilder.path("todos", () -> {
            ApiBuilder.get(todoController::getListOfUserTodos);
            ApiBuilder.post(todoController::createNewTodo);
            ApiBuilder.path("{todoId}", () -> {
                ApiBuilder.get(todoController::getTodosById);
                ApiBuilder.patch(todoController::updateTodo);
                ApiBuilder.delete(todoController::deleteTodo);
            });
            ApiBuilder.path("all-todos", () -> {
                ApiBuilder.get(todoController::getAllTodos);
            });
            ApiBuilder.path("transfer", () -> {
                ApiBuilder.post(todoController::transferTodoToAnotherUser);
            });
        });
    }
}
