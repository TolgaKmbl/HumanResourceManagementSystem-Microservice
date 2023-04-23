package com.tolgakumbul.LoggingService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "CompanyStaff")
@Data
public class CompanyStaffDto {

    @Id
    private String id;

    private Long userId;
    private String firstName;
    private String lastName;
    private String isApproved;
    private LocalDateTime approvalDate;
}
