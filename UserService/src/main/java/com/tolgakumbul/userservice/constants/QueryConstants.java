package com.tolgakumbul.userservice.constants;

public class QueryConstants {

    public static final String SELECT_ALL_QUERY = "SELECT * ";

    public static final String SELECT_QUERY = "SELECT ";

    public static final String FROM_QUERY = "FROM ";

    public static final String WHERE_QUERY = "WHERE ";

    public static final String INSERT_QUERY = "INSERT INTO ";

    public static final String DELETE_QUERY = "DELETE ";

    public static final String UPDATE_QUERY = "UPDATE ";

    /*COMPANY STAFF*/
    public static final String SELECT_ALL_COMPANY_STAFF_QUERY = SELECT_ALL_QUERY +
            FROM_QUERY +
            "COMPANY_STAFF";

    public static final String SELECT_COMPANY_STAFF_BY_ID_QUERY = SELECT_QUERY +
            "USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED " +
            FROM_QUERY +
            "COMPANY_STAFF " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String SELECT_COMPANY_STAFF_BY_NAME_QUERY = SELECT_QUERY +
            "USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED " +
            FROM_QUERY +
            "COMPANY_STAFF " +
            WHERE_QUERY + "FIRST_NAME = ? AND LAST_NAME = ?";

    public static final String INSERT_COMPANY_STAFF_QUERY = INSERT_QUERY +
            "COMPANY_STAFF(USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String DELETE_COMPANY_STAFF_QUERY = UPDATE_QUERY +
            "COMPANY_STAFF " +
            "SET IS_DELETED = ? " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String APPROVE_COMPANY_STAFF_QUERY = UPDATE_QUERY +
            "COMPANY_STAFF " +
            "SET IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ? " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String UPDATE_COMPANY_STAFF_QUERY = UPDATE_QUERY +
            "COMPANY_STAFF " +
            "SET FIRST_NAME = ?, LAST_NAME = ?, IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?" +
            WHERE_QUERY + "USER_ID = ?";

    public static final String GET_LATEST_COMPANY_STAFF_ID_QUERY = "SELECT\n" +
            "    MAX(USER_ID)\n" +
            "FROM\n" +
            "    COMPANY_STAFF";

    /*EMPLOYERS*/
    public static final String SELECT_ALL_EMPLOYERS_QUERY = SELECT_ALL_QUERY +
            FROM_QUERY +
            "EMPLOYERS";

    public static final String SELECT_EMPLOYER_BY_ID_QUERY = SELECT_QUERY +
            "USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED " +
            FROM_QUERY +
            "EMPLOYERS " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String SELECT_EMPLOYERS_BY_COMPANY_NAME_QUERY = SELECT_QUERY +
            "USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED " +
            FROM_QUERY +
            "EMPLOYERS " +
            WHERE_QUERY + "COMPANY_NAME = ?";

    public static final String UPDATE_EMPLOYER_QUERY = UPDATE_QUERY +
            "EMPLOYERS " +
            "SET COMPANY_NAME = ?, WEBSITE = ?, PHONE_NUM = ?, COMPANY_IMG_URL = ?, UPDATED_BY = ?, UPDATED_AT = ? " +
            WHERE_QUERY + "USER_ID = ?";


    public static final String INSERT_EMPLOYER_QUERY = INSERT_QUERY +
            "EMPLOYERS(USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String DELETE_EMPLOYER_QUERY = UPDATE_QUERY +
            "EMPLOYERS " +
            "SET IS_DELETED = ? " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String GET_LATEST_EMPLOYER_ID_QUERY = "SELECT\n" +
            "    MAX(USER_ID)\n" +
            "FROM\n" +
            "    EMPLOYERS";

    /*JOB SEEKERS*/
    public static final String SELECT_ALL_JOB_SEEKERS_QUERY = SELECT_ALL_QUERY +
            FROM_QUERY +
            "JOB_SEEKERS";

    public static final String SELECT_JOB_SEEKER_BY_ID_QUERY = SELECT_QUERY +
            "USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED " +
            FROM_QUERY +
            "JOB_SEEKERS " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String SELECT_JOB_SEEKER_BY_NATIONAL_QUERY = SELECT_QUERY +
            "USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED " +
            FROM_QUERY +
            "JOB_SEEKERS " +
            WHERE_QUERY + "NATIONAL_ID = ?";

    public static final String SELECT_JOB_SEEKER_BY_NAME_QUERY = SELECT_QUERY +
            "USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED " +
            FROM_QUERY +
            "JOB_SEEKERS " +
            WHERE_QUERY + "FIRST_NAME = ? AND LAST_NAME = ?";

    public static final String UPDATE_JOB_SEEKER_QUERY = UPDATE_QUERY +
            "JOB_SEEKERS " +
            "SET FIRST_NAME = ?, LAST_NAME = ?, NATIONAL_ID = ?, BIRTH_DATE = ?, CV_ID = ?, IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?  " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String INSERT_JOB_SEEKER_QUERY = INSERT_QUERY +
            "JOB_SEEKERS(USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String APPROVE_JOB_SEEKER_QUERY = UPDATE_QUERY +
            "JOB_SEEKERS " +
            "SET IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?  " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String DELETE_JOB_SEEKER_QUERY = UPDATE_QUERY +
            "JOB_SEEKERS " +
            "SET IS_DELETED = ? " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String GET_LATEST_JOB_SEEKER_ID_QUERY = "SELECT\n" +
            "    MAX(USER_ID)\n" +
            "FROM\n" +
            "    JOB_SEEKERS";

    //TOTAL ROW COUNT

    public static final String GET_TOTAL_ROW_COUNT = "SELECT COUNT(*) FROM ";
}
