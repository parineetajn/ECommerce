package com.example.Project.ECommerce.PasswordValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({FIELD,METHOD,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface passwordValidatorConstraint {
    String message() default "Password should contain 8-15 characters with at least 1 Lowercase, 1 Uppercase, 1 Special Character and 1 Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
