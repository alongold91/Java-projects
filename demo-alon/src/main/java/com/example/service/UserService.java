package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.dto.CreateUserRequest;
import com.example.dto.UpdateUserRequest;
import com.example.dto.UserResponse;
import com.example.model.User;
import com.example.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToResponse);
    }

    public UserResponse createUser(CreateUserRequest request) {
        User user = new User(null, request.getFirstName(), request.getLastName(), request.getEmail(),
                request.getPassword());
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    public Optional<UserResponse> updateUser(Long userId, UpdateUserRequest request) {
        Optional<User> updatedUser = userRepository.updateUser(userId, request.getFirstName(), request.getLastName(),
                request.getEmail(), request.getPassword());
        return updatedUser.map(this::convertToResponse);
    }

    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt());
    }
}
