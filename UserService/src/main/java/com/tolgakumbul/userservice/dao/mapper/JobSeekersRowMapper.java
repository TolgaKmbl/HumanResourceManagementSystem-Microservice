package com.tolgakumbul.userservice.dao.mapper;

import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JobSeekersRowMapper implements RowMapper<JobSeekersEntity> {
    @Override
    public JobSeekersEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        JobSeekersEntity jobSeekersEntity = new JobSeekersEntity();
        jobSeekersEntity.setUserId(rs.getLong("USER_ID"));
        jobSeekersEntity.setFirstName(StringUtils.defaultString(rs.getString("FIRST_NAME"), " "));
        jobSeekersEntity.setLastName(StringUtils.defaultString(rs.getString("LAST_NAME"), " "));
        jobSeekersEntity.setNationalId(StringUtils.defaultString(rs.getString("NATIONAL_ID"), " "));
        jobSeekersEntity.setCvId(rs.getLong("CV_ID"));
        jobSeekersEntity.setIsApproved(StringUtils.defaultString(rs.getString("IS_APPROVED"), " "));
        Timestamp approvalDate = rs.getTimestamp("APPROVAL_DATE");
        if (approvalDate == null) {
            jobSeekersEntity.setApprovalDate(LocalDateTime.of(1900, 1, 1, 0, 0, 0, 0));
        } else {
            jobSeekersEntity.setApprovalDate(approvalDate.toLocalDateTime());
        }
        Timestamp birthDate = rs.getTimestamp("BIRTH_DATE");
        if (approvalDate == null) {
            jobSeekersEntity.setBirthDate(LocalDateTime.of(1900, 1, 1, 0, 0, 0, 0));
        } else {
            jobSeekersEntity.setBirthDate(birthDate.toLocalDateTime());
        }
        return jobSeekersEntity;
    }
}
