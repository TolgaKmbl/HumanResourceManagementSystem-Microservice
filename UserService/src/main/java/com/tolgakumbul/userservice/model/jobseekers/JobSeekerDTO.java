package com.tolgakumbul.userservice.model.jobseekers;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class JobSeekerDTO implements Serializable {

    private static final long serialVersionUID = -8601469500698783982L;

    private Long userId;
    private String firstName;
    private String lastName;
    private String nationalId;
    private LocalDateTime birthDate;
    private Long cvId;
    private String isApproved;
    private LocalDateTime approvalDate;
    private String isDeleted;
}
