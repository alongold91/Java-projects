package com.example.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateTodoRequest {
    @NotBlank(message = "Header can't be empty")
    @NotNull(message = "Header can't be null")
    private String header;
    @NotBlank(message = "Content can't be empty")
    @NotNull(message = "Content can't be null")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDateTime;
    // TODO: We can create a custom validation that checks that the user with this
    // user id is present on our database
    @NotNull(message = "User id can't be null")
    @Positive(message = "User id must be a positive number")
    private Long userId;

    public CreateTodoRequest() {
    }

    public CreateTodoRequest(
            String header,
            String content,
            LocalDateTime dueDateTime,
            Long userId) {
        this.header = header;
        this.content = content;
        this.dueDateTime = dueDateTime;
        this.userId = userId;
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
}
