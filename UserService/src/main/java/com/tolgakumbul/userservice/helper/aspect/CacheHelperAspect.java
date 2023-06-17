package com.tolgakumbul.userservice.helper.aspect;

import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.helper.model.hazelcast.HazelcastCacheModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Aspect
@Component
public class CacheHelperAspect {

    private static final Logger LOGGER = LogManager.getLogger(CacheHelperAspect.class);

    private final HazelcastCacheHelper cacheManager;

    @Autowired
    public CacheHelperAspect(HazelcastCacheHelper cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Around(value = "@annotation(cacheHelper)",
    argNames = "joinPoint, cacheHelper")
    public Object cacheableDbResultAdvice(ProceedingJoinPoint joinPoint, CacheHelper cacheHelper) throws Throwable {
        String keyName = cacheHelper.keyName();
        HazelcastCacheModel cacheModel = new HazelcastCacheModel();
        cacheModel.setMapName(cacheHelper.mapName());
        cacheModel.setKeyName(keyName);

        Object result = cacheManager.get(cacheModel);
        if (result != null) {
            LOGGER.info("{} found on cache {}", keyName, result);
            return result;
        }
        LOGGER.info("{} has not found on cache", keyName);
        result = joinPoint.proceed();
        if(!ObjectUtils.isEmpty(result)){
            cacheModel.setCachedObject(result);
            LOGGER.info("{} put on cache {}", keyName, result);
            cacheManager.put(cacheModel);
        }

        return result;
    }

}
