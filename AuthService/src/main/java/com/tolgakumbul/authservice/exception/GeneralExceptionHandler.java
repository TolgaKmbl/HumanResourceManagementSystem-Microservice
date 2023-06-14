package com.tolgakumbul.authservice.exception;

import com.tolgakumbul.authservice.service.impl.JwtServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundExceptionHandler(UsernameNotFoundException exception) {
        errorLogger(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<?> userCreateExceptionHandler(UserCreateException exception) {
        errorLogger(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException exception) {
        errorLogger(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    private void errorLogger(Exception exception) {
        LOGGER.error("Handled exception: ", exception);
    }

}
