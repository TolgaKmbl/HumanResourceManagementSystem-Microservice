package com.tolgakumbul.userservice.dao.mapper;

import com.tolgakumbul.userservice.entity.CompanyStaff;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyStaffRowMapper implements RowMapper<CompanyStaff> {

    @Override
    public CompanyStaff mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompanyStaff companyStaff = new CompanyStaff();
        companyStaff.setUserId(rs.getLong("USER_ID"));
        companyStaff.setFirstName(StringUtils.defaultString(rs.getString("FIRST_NAME"), ""));
        companyStaff.setLastName(StringUtils.defaultString(rs.getString("LAST_NAME"), ""));
        companyStaff.setStatus(StringUtils.defaultString(rs.getString("STATUS"), ""));
        companyStaff.setApprovalDate(rs.getTimestamp("APPROVAL_DATE").toLocalDateTime());
        return companyStaff;
    }

}
