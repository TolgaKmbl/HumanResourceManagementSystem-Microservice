package com.tolgakumbul.customcontext;

public interface CustomSecurityContextService {

    CustomSecurityContext getContext();

    void setContext(CustomSecurityContext context);

}
