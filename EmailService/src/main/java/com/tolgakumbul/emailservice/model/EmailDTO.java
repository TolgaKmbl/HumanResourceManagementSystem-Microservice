package com.tolgakumbul.emailservice.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EmailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3184367287561959902L;

    private Integer userId;
    private String email;
}
