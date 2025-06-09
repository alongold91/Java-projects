package com.example.validation;

import com.example.dto.GetListOfUserTodosRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OffsetAndLimitValidator implements ConstraintValidator<OffsetAndLimit, GetListOfUserTodosRequest> {

    @Override
    public boolean isValid(GetListOfUserTodosRequest request, ConstraintValidatorContext context) {
        Integer offset = request.getOffset();
        Integer limit = request.getLimit();
        return (offset == null && limit == null) || (offset != null && limit != null);
    }
    
}
