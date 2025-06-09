// ApplicationConfig.java
package com.interview.config;

import com.interview.controller.UserController;
import com.interview.service.UserService;
import com.interview.validation.UniqueEmailValidator;
import com.interview.repository.UserRepository;
import com.interview.routes.UserRoutes;
import com.interview.routes.AppRoutes;

public class ApplicationConfig {

    // Repositories
    private final UserRepository userRepository;

    // Services
    private final UserService userService;

    // Controllers
    private final UserController userController;

    // Routes
    private final UserRoutes userRoutes;
    private final AppRoutes appRoutes;

    public ApplicationConfig() {
        // Initialize repositories
        this.userRepository = new UserRepository();

        // Configure custom validators with repository access
        UniqueEmailValidator.setUserRepository(userRepository);
        // Initialize services (inject repositories)
        this.userService = new UserService(userRepository);
        // Initialize controllers (inject services)
        this.userController = new UserController(userService);
        // Initialize routes (inject controllers)
        this.userRoutes = new UserRoutes(userController);
        
        this.appRoutes = new AppRoutes(userRoutes);
    }

    // Getters for what we need to expose
    public AppRoutes getAppRoutes() {
        return appRoutes;
    }

    // Optional: Expose individual components if needed
    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserController getUserController() {
        return userController;
    }
}