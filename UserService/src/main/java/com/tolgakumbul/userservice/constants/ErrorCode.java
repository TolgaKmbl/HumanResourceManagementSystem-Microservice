package com.tolgakumbul.userservice.constants;

public class ErrorCode {

    public static final String ALL_COMPANY_STAFF_FETCH_ERROR = "ERRMSGCMPNY001";
    public static final String COMPANY_STAFF_BY_ID_FETCH_ERROR = "ERRMSGCMPNY002";
    public static final String COMPANY_STAFF_BY_NAME_FETCH_ERROR = "ERRMSGCMPNY003";
    public static final String COMPANY_STAFF_INSERT_ERROR = "ERRMSGCMPNY004";
    public static final String COMPANY_STAFF_DELETE_ERROR = "ERRMSGCMPNY005";
    public static final String COMPANY_STAFF_APPROVE_ERROR = "ERRMSGCMPNY006";
    public static final String COMPANY_STAFF_UPDATE_ERROR = "ERRMSGCMPNY007";

    public static final String ALL_EMPLOYERS_FETCH_ERROR = "ERRMSGEMPLYRS001";
    public static final String EMPLOYER_BY_ID_FETCH_ERROR = "ERRMSGEMPLYRS002";
    public static final String EMPLOYER_BY_COMPANY_NAME_FETCH_ERROR = "ERRMSGEMPLYRS003";
    public static final String EMPLOYER_INSERT_ERROR = "ERRMSGEMPLYRS004";
    public static final String EMPLOYER_DELETE_ERROR = "ERRMSGEMPLYRS005";
    public static final String EMPLOYER_UPDATE_ERROR = "ERRMSGEMPLYRS006";

    private ErrorCode() {
    }


}
