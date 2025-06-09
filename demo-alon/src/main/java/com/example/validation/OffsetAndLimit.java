
package com.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OffsetAndLimitValidator.class)
public @interface OffsetAndLimit {
    String message() default "Offset and limit must be passed together";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
