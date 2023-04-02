package com.tolgakumbul.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserCreateException extends RuntimeException{

    public UserCreateException(String message) {
        super(message);
    }

}
