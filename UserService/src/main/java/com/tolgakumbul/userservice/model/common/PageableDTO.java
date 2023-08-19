package com.tolgakumbul.userservice.model.common;

import lombok.Data;

@Data
public class PageableDTO {

    private Long pageSize;
    private Long pageNumber;
    private String sortColumn;
    private String sortType;
}
