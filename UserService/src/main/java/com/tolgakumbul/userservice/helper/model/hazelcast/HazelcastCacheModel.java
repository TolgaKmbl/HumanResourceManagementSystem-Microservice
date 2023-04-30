package com.tolgakumbul.userservice.helper.model.hazelcast;

import lombok.Data;

@Data
public class HazelcastCacheModel {

    private String mapName;
    private String keyName;
    private Object cachedObject;

}
