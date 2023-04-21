package com.tolgakumbul.userservice.model.common;

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
