package com.tolgakumbul.userservice.model;

public enum IsApprovedEnum {
    ACTIVE("A"), PASSIVE("P");

    private final String textType;

    IsApprovedEnum(String textType) {
        this.textType = textType;
    }

    public String getTextType() {
        return this.textType;
    }
}
