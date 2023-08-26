package com.tolgakumbul.userservice.helper.aspect;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.entity.BaseDaoEntity;
import com.tolgakumbul.userservice.helper.SecurityContextHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditHelperAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditHelperAspect.class);

    @Before(value = "@annotation(auditHelper)",
            argNames = "joinPoint, auditHelper")
    public void auditHelper(JoinPoint joinPoint, AuditHelper auditHelper) {
        Object[] args = joinPoint.getArgs();
        BaseDaoEntity baseDaoEntity = (BaseDaoEntity) args[0];
        //TODO: do not insert null, pass the same value
        if (auditHelper.sqlQuery().equalsIgnoreCase(Constants.SQL_INSERT)) {
            baseDaoEntity.setCreatedAt(LocalDateTime.now());
            baseDaoEntity.setCreatedBy(SecurityContextHelper.getUserName());
        } else if (auditHelper.sqlQuery().equalsIgnoreCase(Constants.SQL_UPDATE)) {
            baseDaoEntity.setUpdatedAt(LocalDateTime.now());
            baseDaoEntity.setUpdatedBy(SecurityContextHelper.getUserName());
        }
        LOGGER.info("BaseDaoEntity is set by AuditHelperAspect: {}", baseDaoEntity);
    }
}
