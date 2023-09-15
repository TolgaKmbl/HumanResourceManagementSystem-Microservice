package com.tolgakumbul.userservice.model.jobseekers;

import com.tolgakumbul.userservice.model.common.PaginationMetadataDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class JobSeekerListResponseDTO implements Serializable {

    private static final long serialVersionUID = -8458313632084898397L;

    private List<JobSeekerDTO> jobSeekerList;
    private PaginationMetadataDTO paginationMetadata;
}
