package com.tolgakumbul.userservice.exception;

import com.tolgakumbul.userservice.exception.model.GeneralErrorResponse;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ExceptionMessageUtil {

    public static GeneralErrorResponse handleErrorResponse(UsersException usersException) {
        GeneralErrorResponse generalErrorResponse = new GeneralErrorResponse();
        generalErrorResponse.setErrorCode(usersException.getMessage());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
        if (usersException.getArgs() == null || usersException.getArgs().length == 0) {
            generalErrorResponse.setErrorMessage(resourceBundle.getString(usersException.getMessage()));
        } else {
            generalErrorResponse.setErrorMessage(MessageFormat.format(resourceBundle.getString(usersException.getMessage()), usersException.getArgs()));
        }
        return generalErrorResponse;
    }

}
