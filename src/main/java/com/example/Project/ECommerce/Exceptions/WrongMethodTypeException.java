package com.example.Project.ECommerce.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//error 405
//if wrong method type is used to access an API
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class WrongMethodTypeException extends RuntimeException {
    public WrongMethodTypeException(String message) {
        super(message);
    }
}
