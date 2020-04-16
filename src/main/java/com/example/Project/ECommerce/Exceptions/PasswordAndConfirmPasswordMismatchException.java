package com.example.Project.ECommerce.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PasswordAndConfirmPasswordMismatchException extends RuntimeException{
    public PasswordAndConfirmPasswordMismatchException(String message) {
        super(message);
    }
}
