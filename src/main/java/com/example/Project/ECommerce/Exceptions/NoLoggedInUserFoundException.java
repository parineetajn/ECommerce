package com.example.Project.ECommerce.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//error 401
//if there is no logged-in user found while accessing the API
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoLoggedInUserFoundException extends RuntimeException {
    public NoLoggedInUserFoundException(String message) {
        super(message);
    }
}
