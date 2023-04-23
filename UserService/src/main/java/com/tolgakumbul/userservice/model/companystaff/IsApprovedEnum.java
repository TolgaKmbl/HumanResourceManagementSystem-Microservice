package com.tolgakumbul.userservice.model.companystaff;

import lombok.Getter;

import java.io.Serializable;

public enum IsApprovedEnum implements Serializable {

    ACTIVE("A"), PASSIVE("P");

    @Getter
    private final String textType;

    IsApprovedEnum(String textType) {
        this.textType = textType;
    }

    public static IsApprovedEnum fromTextType(String textType) {
        if(textType == null){
            return IsApprovedEnum.PASSIVE;
        }
        for (IsApprovedEnum e : IsApprovedEnum.values()) {
            if (e.textType.equals(textType)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with textType " + textType);
    }

}
