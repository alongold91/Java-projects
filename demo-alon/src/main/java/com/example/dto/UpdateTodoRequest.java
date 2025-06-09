package com.example.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateTodoRequest {
    @NotNull(message = "Todo id can't be null")
    @Positive(message = "Todo id must be a positive number")
    private Long todoId;
    @NotBlank(message = "Header can't be an empty string, either pass an updated value or nothing at att")
    private String header;
    @NotBlank(message = "Content can't be an empty string, either pass an updated value or nothing at att")
    private String content;
    private LocalDateTime dueDateTime;
    @NotNull(message = "User id can't be null")
    @Positive(message = "User id must be a positive number")
    private Long userId;
    boolean completed;

    public UpdateTodoRequest() {
    }

    public UpdateTodoRequest(
            Long todoId,
            String header,
            String content,
            LocalDateTime dueDateTime,
            Long userId,
            boolean completed) {
        this.todoId = todoId;
        this.header = header;
        this.content = content;
        this.dueDateTime = dueDateTime;
        this.userId = userId;
        this.completed = completed;
    }

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
