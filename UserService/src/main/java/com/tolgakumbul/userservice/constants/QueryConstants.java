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

    public static final String DELETE_COMPANY_STAFF_QUERY = DELETE_QUERY + FROM_QUERY +
            "COMPANY_STAFF " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String APPROVE_COMPANY_STAFF_QUERY = UPDATE_QUERY +
            "COMPANY_STAFF " +
            "SET IS_APPROVED = ? " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String UPDATE_COMPANY_STAFF_QUERY = UPDATE_QUERY +
            "COMPANY_STAFF " +
            "SET FIRST_NAME = ?, LAST_NAME = ?, IS_APPROVED = ?, APPROVAL_DATE = ?" +
            WHERE_QUERY + "USER_ID = ?";

    /*EMPLOYERS*/
    public static final String SELECT_ALL_EMPLOYERS_QUERY = SELECT_ALL_QUERY +
            FROM_QUERY +
            "EMPLOYERS";

    public static final String SELECT_EMPLOYER_BY_ID_QUERY = SELECT_QUERY +
            "USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL " +
            FROM_QUERY +
            "EMPLOYERS " +
            WHERE_QUERY + "USER_ID = ?";

    public static final String SELECT_EMPLOYERS_BY_COMPANY_NAME_QUERY = SELECT_QUERY +
            "USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL " +
            FROM_QUERY +
            "EMPLOYERS " +
            WHERE_QUERY + "COMPANY_NAME = ?";

    public static final String UPDATE_EMPLOYER_QUERY = UPDATE_QUERY +
            "EMPLOYERS " +
            "SET COMPANY_NAME = ?, WEBSITE = ?, PHONE_NUM = ?, COMPANY_IMG_URL = ? " +
            WHERE_QUERY + "USER_ID = ?";


    public static final String INSERT_EMPLOYER_QUERY = INSERT_QUERY +
            "EMPLOYERS(USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL) " +
            "VALUES (?, ?, ?, ?, ?)";

    public static final String DELETE_EMPLOYER_QUERY = DELETE_QUERY + FROM_QUERY +
            "EMPLOYERS " +
            WHERE_QUERY + "USER_ID = ?";
}
