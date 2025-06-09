// UniqueEmailValidator.java
package com.interview.validation;

import com.interview.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private static UserRepository userRepository;
    
    // Static method to inject repository (we'll call this from ApplicationConfig)
    public static void setUserRepository(UserRepository repository) {
        userRepository = repository;
    }
     
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || userRepository == null) {
            return true; // Let @NotBlank handle null values
        }
        
        // Check if email exists in database
        return !userRepository.existsByEmail(email);
    }
}