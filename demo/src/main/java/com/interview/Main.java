// Main.java
package com.interview;

import com.interview.config.ApplicationConfig;
import com.interview.config.JacksonConfig;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class Main {
    public static void main(String[] args) {
        // Initialize application configuration
        ApplicationConfig config = new ApplicationConfig();
        
        // Create Javalin app with custom Jackson configuration
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.jsonMapper(new JavalinJackson(JacksonConfig.createObjectMapper()));
            javalinConfig.plugins.enableCors(cors -> {
                cors.add(corsConfig -> corsConfig.anyHost());
            });
        }).start(7000);
        
        // Global exception handler
        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
        });
        
        // Register all routes
        config.getAppRoutes().registerAll(app);
        
        System.out.println("Server started on http://localhost:7000");
        System.out.println("API endpoints available at http://localhost:7000/api/");
    }
    
    static class ErrorResponse {
        public String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}