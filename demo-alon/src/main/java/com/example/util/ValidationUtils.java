package com.example.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class ValidationUtils {

    private static final Validator VALIDATOR;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    public static <T> Optional<List<String>> validateAndGetErrors(T request) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(request);
        if (violations.isEmpty()) {
            return Optional.empty();
        }
        List<String> errorMessages = violations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return Optional.of(errorMessages);
    }
}
