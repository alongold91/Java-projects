package com.example.controller;

import java.util.List;

import com.example.dto.CreateTodoRequest;
import com.example.dto.ErrorResponse;
import com.example.dto.GetListOfUserTodosRequest;
import com.example.dto.TransferTodoRequest;
import com.example.dto.UpdateTodoRequest;
import com.example.model.Todo;
import com.example.service.TodoService;
import com.example.util.ErrorUtils;
import com.example.util.ValidationUtils;

import io.javalin.http.Context;

public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    public void getAllTodos(Context ctx) {
        try {
            ctx.status(200).json(todoService.getAllTodos());
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void getListOfUserTodos(Context ctx) {
        try {
            long userId = Long.parseLong(ctx.pathParam("userId"));
            int offset = ctx.queryParamAsClass("offset", Integer.class).getOrDefault(0);
            int limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
            GetListOfUserTodosRequest request = new GetListOfUserTodosRequest(userId, offset, limit);
            ValidationUtils.validateAndGetErrors(request).ifPresentOrElse(
                    error -> ctx.status(400).json(new ErrorResponse(error)),
                    () -> {
                        todoService.getListOfUserTodos(request).ifPresentOrElse(res -> ctx.status(200).json(res),
                                () -> ctx.status(404).json(new ErrorResponse(List.of("List not found"))));

                    });

        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void getTodosById(Context ctx) {
        try {

            long todoId = Long.parseLong(ctx.pathParam("todoId"));
            todoService.getTodosByTodoId(todoId).ifPresentOrElse(res -> ctx.status(200).json(res),
                    () -> ctx.status(404).json(new ErrorResponse(List.of("Todo  not found"))));

        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void createNewTodo(Context ctx) {
        try {
            long userId = Long.parseLong(ctx.pathParam("userId"));
            CreateTodoRequest request = ctx.bodyAsClass(CreateTodoRequest.class);
            request.setUserId(userId);
            ValidationUtils.validateAndGetErrors(request).ifPresentOrElse(
                    error -> ctx.status(400).json(new ErrorResponse(error)),
                    () -> {
                        Todo response = todoService.createTodo(request);
                        ctx.status(201).json(response);
                    });
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void updateTodo(Context ctx) {
        try {
            long userId = Long.parseLong(ctx.pathParam("userId"));
            long todoId = Long.parseLong(ctx.pathParam("todoId"));
            UpdateTodoRequest request = ctx.bodyAsClass(UpdateTodoRequest.class);
            request.setUserId(userId);
            request.setTodoId(todoId);
            ValidationUtils.validateAndGetErrors(request)
                    .ifPresentOrElse(error -> ctx.status(400).json(new ErrorResponse(error)), () -> {
                        todoService.updateTodo(request).ifPresentOrElse(res -> ctx.status(201).json(res),
                                () -> ctx.status(404).json(List.of("Todo not found")));
                    });
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

    public void transferTodoToAnotherUser(Context ctx) {
        try {
            long userId = Long.parseLong(ctx.pathParam("userId"));
            long todoId = Long.parseLong(ctx.pathParam("todoId"));
            TransferTodoRequest request = ctx.bodyAsClass(TransferTodoRequest.class);
            request.setFromUserId(userId);
            request.setTodoId(todoId);
            ValidationUtils.validateAndGetErrors(request)
                    .ifPresentOrElse(error -> ctx.status(400).json(new ErrorResponse(error)), () -> {
                        todoService.transferTodoToAnotherUser(request).ifPresentOrElse(res -> ctx.status(201).json(res),
                                () -> ctx.status(404).json(List.of("Todo or users not found")));
                    });
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }
    // Could be more useful since we get the user id from the url but let's leave it for the sake of practice
    public void deleteTodo(Context ctx) {
        try {
            long todoId = Long.parseLong(ctx.pathParam("todoId"));

            if (todoService.deleteTodo(todoId)) {
                ctx.status(204).json("Todo Deleted");
            } else {
                ctx.status(404).json(new ErrorResponse(List.of("Todo not found")));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new ErrorResponse(List.of("Invalid todo ID")));
        } catch (Exception e) {
            ErrorUtils.genarateInternalServerError(ctx);
        }
    }

}
