package com.tolgakumbul.userservice.model.employers;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployersDTO implements Serializable {

    private static final long serialVersionUID = 1850542815173255128L;

    private Long userId;
    private String companyName;
    private String website;
    private String phoneNum;
    private String companyImgUrl;
    private String isDeleted;
}
