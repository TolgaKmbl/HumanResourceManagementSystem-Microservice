package com.tolgakumbul.userservice.dao.mapper;

import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CompanyStaffRowMapper implements RowMapper<CompanyStaffEntity> {

    @Override
    public CompanyStaffEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompanyStaffEntity companyStaffEntity = new CompanyStaffEntity();
        companyStaffEntity.setUserId(rs.getLong("USER_ID"));
        companyStaffEntity.setFirstName(StringUtils.defaultString(rs.getString("FIRST_NAME"), " "));
        companyStaffEntity.setLastName(StringUtils.defaultString(rs.getString("LAST_NAME"), " "));
        companyStaffEntity.setIsApproved(StringUtils.defaultString(rs.getString("IS_APPROVED"), " "));
        Timestamp approvalDate = rs.getTimestamp("APPROVAL_DATE");
        if(approvalDate == null){
            companyStaffEntity.setApprovalDate(LocalDateTime.of(1900,1,1,0,0,0,0));
        }else {
            companyStaffEntity.setApprovalDate(approvalDate.toLocalDateTime());
        }
        return companyStaffEntity;
    }

}
