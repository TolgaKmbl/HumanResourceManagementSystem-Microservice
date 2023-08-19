package com.tolgakumbul.userservice.dao.model;

import lombok.Data;

@Data
public class Pageable {

    private Long pageSize;
    private Long pageNumber;
    private String sortColumn;
    private String sortType;
}
