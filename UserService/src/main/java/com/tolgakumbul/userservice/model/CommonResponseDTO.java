package com.tolgakumbul.userservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CommonResponseDTO {

    private Integer returnCode;
    private String reasonCode;
}
