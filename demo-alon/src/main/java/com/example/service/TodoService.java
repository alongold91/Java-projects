package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.dto.CreateTodoRequest;
import com.example.dto.GetListOfUserTodosRequest;
import com.example.dto.TransferTodoRequest;
import com.example.dto.UpdateTodoRequest;
import com.example.model.Todo;
import com.example.repository.TodoRepository;

public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAllTodos();
    }

    public Optional<List<Todo>> getListOfUserTodos(GetListOfUserTodosRequest request) {
        return todoRepository.findListOfTodosByUserId(request.getUserId(), request.getOffset(), request.getLimit());
    }

    public Optional<Todo> getTodosByTodoId(long todoId) {
        return todoRepository.findById(todoId);
    }

    public Todo createTodo(CreateTodoRequest request) {
        Todo todoToBeCreated = new Todo(null, request.getHeader(), request.getContent(), request.getDueDateTime(),
                request.getUserId(), LocalDateTime.now(), LocalDateTime.now(), false);
        return todoRepository.createTodo(todoToBeCreated);
    }

    public Optional<Todo> updateTodo(UpdateTodoRequest request) {
        return todoRepository.updateTodo(request.getTodoId(), request.getHeader(), request.getContent(),
                request.getDueDateTime(), request.getUserId(), LocalDateTime.now(), request.isCompleted());
    }


    public Optional<Todo> transferTodoToAnotherUser(TransferTodoRequest request) {
        return todoRepository.transferTodoToUser(request.getTodoId(), request.getFromUserId(), request.getToUserId());
    }

    public boolean deleteTodo(long todoId) {
        return todoRepository.deleteByTodoId(todoId);
    }


}
