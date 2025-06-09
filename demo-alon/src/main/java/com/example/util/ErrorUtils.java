package com.example.util;

import java.util.List;

import com.example.dto.ErrorResponse;

import io.javalin.http.Context;

public class ErrorUtils {
    public static void genarateInternalServerError(Context ctx) {
        ctx.status(500).json(new ErrorResponse(List.of("Internal Server Error")));
    }
}
