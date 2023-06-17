package com.tolgakumbul.userservice.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
public class BaseDaoEntity {

    @Column("CREATED_BY")
    private String createdBy;
    @Column("UPDATED_BY")
    private String updatedBy;
    @Column("CREATED_AT")
    private LocalDateTime createdAt;
    @Column("UPDATED_AT")
    private LocalDateTime updatedAt;
}
