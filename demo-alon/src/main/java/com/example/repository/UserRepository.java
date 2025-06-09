package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.example.model.User;

public class UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Read
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    // Read
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    // Create
    public User save(User user) {
        user.setId(idGenerator.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    // Update
    public Optional<User> updateUser(Long userId, String firstName, String lastName, String email, String password) {
        if (userId == null || (firstName == null && lastName == null && email == null & password == null)) {
            return Optional.empty();
        }

        User existingUser = users.get(userId);
        if (existingUser == null) {
            return Optional.empty();
        }

        if (firstName != null) {
            existingUser.setFirstName(firstName);
        }
        if (lastName != null) {
            existingUser.setLastName(lastName);
        }
        if (email != null) {
            existingUser.setEmail(email);
        }
        if (password != null) {
            existingUser.setPassword(password);
        }

        users.put(userId, existingUser);
        return Optional.of(existingUser);
    }

    // Delete
    public boolean deleteById(Long id) {
        return users.remove(id) != null;
    }

    // Custom validation helper method
    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}