package com.tolgakumbul.userservice.dao.mapper;

import com.tolgakumbul.userservice.entity.EmployersEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployersRowMapper implements RowMapper<EmployersEntity> {

    @Override
    public EmployersEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployersEntity employersEntity = new EmployersEntity();
        employersEntity.setUserId(rs.getLong("USER_ID"));
        employersEntity.setCompanyName(StringUtils.defaultString(rs.getString("COMPANY_NAME"), " "));
        employersEntity.setCompanyImgUrl(StringUtils.defaultString(rs.getString("COMPANY_IMG_URL"), " "));
        employersEntity.setWebsite(StringUtils.defaultString(rs.getString("WEBSITE"), " "));
        employersEntity.setPhoneNum(StringUtils.defaultString(rs.getString("PHONE_NUM"), " "));
        return employersEntity;
    }

}
