package com.tolgakumbul.userservice.helper.model;

import lombok.Data;

@Data
public class HazelcastCacheModel {

    private String mapName;
    private String keyName;
    private Object cachedObject;

}
