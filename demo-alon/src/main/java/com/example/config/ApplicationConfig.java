package com.example.config;

import com.example.controller.TodoController;
import com.example.controller.UserController;
import com.example.repository.TodoRepository;
import com.example.repository.UserRepository;
import com.example.routes.AppRoutes;
import com.example.routes.TodoRoutes;
import com.example.routes.UserRoutes;
import com.example.service.TodoService;
import com.example.service.UserService;
import com.example.validation.UniqueEmailValidator;

public class ApplicationConfig {
    // Repositories
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    // Services
    private final UserService userService;
    private final TodoService todoService;
    // Controllers
    private final UserController userController;
    private final TodoController todoController;
    // Routes
    private final UserRoutes userRoutes;
    private final TodoRoutes todoRoutes;
    private final AppRoutes appRoutes;

    public ApplicationConfig() {
        // Initialize repositories
        this.userRepository = new UserRepository();
        this.todoRepository = new TodoRepository();
        // Configure custom validators with repository access
        UniqueEmailValidator.setUserRepository(userRepository);
        // Initialize services (inject repositories)
        this.userService = new UserService(userRepository);
        this.todoService = new TodoService(todoRepository);
        // Initialize controllers (inject services)
        this.userController = new UserController(userService);
        this.todoController = new TodoController(todoService);
        // Initialize routes (inject controllers)
        this.todoRoutes = new TodoRoutes(todoController);
        this.userRoutes = new UserRoutes(userController, todoRoutes);
        this.appRoutes = new AppRoutes(userRoutes);
    }

    // Getters for what we need to expose
    public AppRoutes getAppRoutes() {
        return appRoutes;
    }
}
