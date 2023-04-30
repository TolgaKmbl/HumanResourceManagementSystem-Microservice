package com.tolgakumbul.userservice.helper;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.tolgakumbul.userservice.helper.model.hazelcast.HazelcastCacheModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HazelcastCacheHelper {

    private static final Logger LOGGER = LogManager.getLogger(HazelcastCacheHelper.class);

    private final HazelcastInstance hazelcastInstance;

    public HazelcastCacheHelper(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public Object get(HazelcastCacheModel cacheModel) {
        Object cachedObject = null;
        try {
            cachedObject = hazelcastInstance.getMap(cacheModel.getMapName()).get(cacheModel.getKeyName());
        } catch (Exception e) {
            LOGGER.error("ERROR while getting {} from cache: {}", cacheModel, e.getMessage());
        }
        return cachedObject;
    }

    public boolean put(HazelcastCacheModel cacheModel) {
        try {
            hazelcastInstance.getMap(cacheModel.getMapName()).put(cacheModel.getKeyName(), cacheModel.getCachedObject());
            return true;
        } catch (Exception e) {
            LOGGER.error("ERROR while putting {} into cache: {}", cacheModel, e.getMessage());
        }
        return false;
    }

    public void removeAll(){
        HazelcastInstance hazelcast = Hazelcast.getHazelcastInstanceByName("hazelcast-instance");
        hazelcast.getDistributedObjects().stream()
                .filter(object -> object instanceof IMap)
                .map(object -> (IMap<Object, Object>) object)
                .forEach(IMap::clear);
    }
}
