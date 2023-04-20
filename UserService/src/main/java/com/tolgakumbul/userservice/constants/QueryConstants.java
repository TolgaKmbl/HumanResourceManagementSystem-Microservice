package com.tolgakumbul.userservice.constants;

public class QueryConstants {

    public static final String SELECT_ALL_QUERY = "SELECT * ";

    public static final String SELECT_QUERY = "SELECT ";

    public static final String FROM_QUERY = "FROM ";

    public static final String WHERE_QUERY = "WHERE ";

    public static final String INSERT_QUERY = "INSERT INTO ";

    public static final String SELECT_ALL_COMPANY_STAFF_QUERY = SELECT_ALL_QUERY +
            FROM_QUERY +
            "COMPANY_STAFF";

    public static final String SELECT_COMPANY_STAFF_BY_ID_QUERY = SELECT_QUERY +
            "USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE " +
            FROM_QUERY +
            "COMPANY_STAFF " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String SELECT_COMPANY_STAFF_BY_NAME_QUERY = SELECT_QUERY +
            "USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE " +
            FROM_QUERY +
            "COMPANY_STAFF " +
            WHERE_QUERY + "FIRST_NAME = ? AND LAST_NAME = ?";

    public static final String INSERT_COMPANY_STAFF_QUERY = INSERT_QUERY +
            "COMPANY_STAFF(USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE) " +
            "VALUES (?, ?, ?, ?, ?)";

}
