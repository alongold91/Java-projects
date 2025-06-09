// AppRoutes.java
package com.interview.routes;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;

public class AppRoutes {
    private final UserRoutes userRoutes;
    
    public AppRoutes(UserRoutes userRoutes) {
        this.userRoutes = userRoutes;
    }
    
    public void registerAll(Javalin app) {
        // Health check route
        app.get("/", ctx -> ctx.json(new HealthResponse("Server is running!", "1.0.0")));
        
        // API routes with /api prefix
        app.routes(() -> {
            ApiBuilder.path("api", () -> {
                // All user routes will be under /api/users
                userRoutes.register(app);
                
                // Future routes can be added here
                // ApiBuilder.path("products", () -> { ... });
                // ApiBuilder.path("orders", () -> { ... });
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