package com.tolgakumbul.authservice.exception;

import com.tolgakumbul.authservice.core.ErrorResult;
import com.tolgakumbul.authservice.helper.LogException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GeneralExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    @LogException
    public ResponseEntity<?> usernameNotFoundExceptionHandler(UsernameNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResult(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserCreateException.class)
    @LogException
    public ResponseEntity<?> userCreateExceptionHandler(UserCreateException exception) {
        return new ResponseEntity<>(new ErrorResult(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @LogException
    public ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException exception) {
        return new ResponseEntity<>(new ErrorResult(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @LogException
    public ResponseEntity<?> expiredJwtExceptionHandler(ExpiredJwtException exception) {
        return new ResponseEntity<>(new ErrorResult(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenException.class)
    @LogException
    public ResponseEntity<?> refreshTokenNotFoundExceptionHandler(RefreshTokenException exception) {
        return new ResponseEntity<>(new ErrorResult(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

}
