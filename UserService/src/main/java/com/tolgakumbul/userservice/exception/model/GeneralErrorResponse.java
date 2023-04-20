package com.tolgakumbul.userservice.exception.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeneralErrorResponse implements Serializable {

    private static final long serialVersionUID = 8118131007515565537L;

    private String errorCode;
    private String errorMessage;

}
