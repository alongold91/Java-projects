package com.example.routes;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;

public class AppRoutes {
    private final UserRoutes userRoutes;

    public AppRoutes(UserRoutes userRoutes) {
        this.userRoutes = userRoutes;
    }

    public void registerAll(Javalin app) {
        app.get("/", ctx -> ctx.json(new HealthResponse("Server is running!", "1.0.0")));
        app.routes(() -> {
            ApiBuilder.path("api", () -> {
                userRoutes.register(app);
            });
        });
    }

    static class HealthResponse {
        public String message;
        public String version;

        public HealthResponse(String message, String version) {
            this.message = message;
            this.version = version;
        }
    }
}
