package com.tolgakumbul.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RefreshTokenException extends RuntimeException{

    public RefreshTokenException(String message) {
        super(message);
    }

}
