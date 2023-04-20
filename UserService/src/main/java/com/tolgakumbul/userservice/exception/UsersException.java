package com.tolgakumbul.userservice.exception;

public class UsersException extends RuntimeException {

    public UsersException(String errorCode) {
        super(errorCode);
    }


}
