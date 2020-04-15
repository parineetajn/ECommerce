package com.example.Project.ECommerce.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Error 403
//if logged_in user does not have required authorization to access the API
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException(String message) {
        super(message);
    }
}

