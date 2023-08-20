package com.tolgakumbul.userservice.model.jobseekers;

import com.tolgakumbul.userservice.model.common.PageableDTO;
import lombok.Data;

@Data
public class GetAllJobSeekersRequestDTO {

    private PageableDTO pageable;
}
