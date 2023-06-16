package com.tolgakumbul.customcontext;

import org.springframework.stereotype.Service;

@Service
public class CustomSecurityContextServiceImpl implements CustomSecurityContextService {

    private CustomSecurityContext context;

    @Override
    public CustomSecurityContext getContext() {
        return context;
    }

    @Override
    public void setContext(CustomSecurityContext context) {
        this.context = context;
    }
}
