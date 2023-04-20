package com.tolgakumbul.userservice.dao.mapper;

import com.tolgakumbul.userservice.entity.CompanyStaff;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CompanyStaffRowMapper implements RowMapper<CompanyStaff> {

    @Override
    public CompanyStaff mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompanyStaff companyStaff = new CompanyStaff();
        companyStaff.setUserId(rs.getLong("USER_ID"));
        companyStaff.setFirstName(StringUtils.defaultString(rs.getString("FIRST_NAME"), ""));
        companyStaff.setLastName(StringUtils.defaultString(rs.getString("LAST_NAME"), ""));
        companyStaff.setIsApproved(StringUtils.defaultString(rs.getString("IS_APPROVED"), ""));
        Timestamp approvalDate = rs.getTimestamp("APPROVAL_DATE");
        if(approvalDate == null){
            companyStaff.setApprovalDate(LocalDateTime.of(1900,1,1,0,0,0,0));
        }else {
            companyStaff.setApprovalDate(approvalDate.toLocalDateTime());
        }
        return companyStaff;
    }

}
