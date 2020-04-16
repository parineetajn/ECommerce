package com.example.Project.ECommerce.PasswordValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidatorConstraint,String> {
    @Override
    public void initialize(PasswordValidatorConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value!=null && value.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}")){
            return true;
        }
        else
            return false;
    }
}
