package com.tolgakumbul.customcontext;


public class CustomSecurityContext {

    private String guid;
    private String token;

    public CustomSecurityContext(String guid, String token) {
        this.guid = guid;
        this.token = token;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
