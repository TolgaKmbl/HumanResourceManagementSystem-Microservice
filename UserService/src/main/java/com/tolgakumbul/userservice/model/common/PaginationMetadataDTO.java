package com.tolgakumbul.userservice.model.common;

import lombok.Data;

@Data
public class PaginationMetadataDTO {

    private Long totalRowCount;
    private Long currentPage;
    private Long pageSize;
    private Boolean hasPreviousPage;
    private Boolean hasNextPage;
}
