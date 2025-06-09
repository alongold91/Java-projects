package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.example.model.Todo;

public class TodoRepository {
    private final Map<Long, TreeMap<Long, Todo>> todosByUser = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Todo> findAllTodos() {
        return todosByUser.values().stream()
                .flatMap(userTodos -> userTodos.values().stream()).collect(Collectors.toList());
    }

    public Optional<List<Todo>> findListOfTodosByUserId(Long userId, Integer offset, Integer limit) {
        if (!todosByUser.containsKey(userId)) {
            return Optional.empty();
        }
        TreeMap<Long, Todo> userTodos = todosByUser.get(userId);
        if (offset == null || limit == null) {
            List<Todo> listOfTodos = userTodos.values().stream().collect(Collectors.toList());
            return Optional.of(listOfTodos);

        }
        List<Todo> paginatedTodos = userTodos.values().stream().skip(offset).limit(limit).collect(Collectors.toList());
        return Optional.of(paginatedTodos);
    }

    public Optional<Todo> findById(Long todoId) {
        return todosByUser.values().stream()
                .flatMap(userTodos -> userTodos.values().stream())
                .filter(todo -> todo.getId().equals(todoId))
                .findFirst();

    }

    public Todo createTodo(Todo todo) {
        todo.setId(idGenerator.getAndIncrement());
        todosByUser.computeIfAbsent(todo.getUserId(), k -> new TreeMap<>()).put(todo.getId(), todo);
        return todo;
    }

    // Update
    public Optional<Todo> updateTodo(Long todoId, String header, String content, LocalDateTime dueDateTime, Long userId,
            LocalDateTime updatedAt, Boolean completed) {
        // update will fail without a todo id, a user id or without any property to
        // update
        if (todoId == null || userId == null || (header == null && content == null && dueDateTime == null
                && updatedAt == null && completed == null)) {
            return Optional.empty();
        }

        TreeMap<Long, Todo> userTodos = todosByUser.get(userId);
        // Always check for null before calling methods on the object!
        if (userTodos == null || userTodos.isEmpty()) {
            return Optional.empty();
        }

        Todo todoToBeUpdated = userTodos.get(todoId);

        if (todoToBeUpdated == null) {
            return Optional.empty();
        }

        if (header != null) {
            todoToBeUpdated.setHeader(header);
        }

        if (content != null) {
            todoToBeUpdated.setContent(content);
        }

        if (dueDateTime != null) {
            todoToBeUpdated.setDueDateTime(dueDateTime);
        }

        if (updatedAt != null) {
            todoToBeUpdated.setUpdatedAt(updatedAt);
        }

        if (completed != null) {
            todoToBeUpdated.setCompleted(completed);
        }

        return Optional.of(todoToBeUpdated);
    }

    // Update
    public Optional<Todo> transferTodoToUser(Long todoId, Long fromUserId, Long toUserId) {
        // Find todo in fromUser's todos
        TreeMap<Long, Todo> oldUserTodos = todosByUser.get(fromUserId);
        // Remove from fromUser
        if (oldUserTodos != null && !oldUserTodos.isEmpty()) {
            Todo todo = oldUserTodos.get(todoId);
            if (todo == null) {
                return Optional.empty(); // Todo not found
            }

            oldUserTodos.remove(todoId);
            todo.setUserId(toUserId);
            todosByUser.computeIfAbsent(toUserId, k -> new TreeMap<>()).put(todoId, todo);
            return Optional.of(todo);
        }

        return Optional.empty();
    }

    // DELETE
    public boolean deleteByTodoId(Long todoId) {
        Optional<Todo> todoOptional = findById(todoId);
        if (todoOptional.isEmpty()) {
            return false;
        }
        Todo todo = todoOptional.get();
        TreeMap<Long, Todo> userTodoList = todosByUser.get(todo.getUserId());
        return userTodoList.remove(todo.getId()) != null;
    }

}
