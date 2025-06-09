package com.example;

import com.example.config.ApplicationConfig;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        ApplicationConfig applicationConfig = new ApplicationConfig();

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.plugins.enableCors(cors -> {
                cors.add(corsConfig -> corsConfig.anyHost());
            });
        }).start(8080);

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500).json(new ErrorResponse("Internal server error " + e.getMessage()));
        });
        // Register all routes
        applicationConfig.getAppRoutes().registerAll(app);

        System.out.println("Server started on http://localhost:8080");
    }

    static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}