package com.tolgakumbul.userservice.model.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CommonResponseDTO implements Serializable {

    private static final long serialVersionUID = -356011973520684349L;

    private Integer returnCode;
    private String reasonCode;
}
