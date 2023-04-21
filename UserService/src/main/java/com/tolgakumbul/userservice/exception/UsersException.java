package com.tolgakumbul.userservice.exception;

import lombok.Getter;

public class UsersException extends RuntimeException {

    @Getter
    private Object[] args;

    public UsersException(String errorCode) {
        super(errorCode);
    }

    public UsersException(String errorCode, Object... args) {
        super(errorCode);
        this.args = args;
    }


}
