package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {
    public List<String> errors;
    @JsonFormat(pattern = "MMM dd, yyyy 'at' HH:mm:ss")
    private LocalDateTime timeStamp;

    // Constructor for multiple errors
    public ErrorResponse(List<String> errors) {
        this.errors = errors;
        this.timeStamp = LocalDateTime.now();
    }

}
