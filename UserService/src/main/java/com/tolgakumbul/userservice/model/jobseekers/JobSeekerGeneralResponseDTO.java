package com.tolgakumbul.userservice.model.jobseekers;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class JobSeekerGeneralResponseDTO implements Serializable {

    private static final long serialVersionUID = 5434234854454142236L;

    private JobSeekerDTO jobSeeker;

}
