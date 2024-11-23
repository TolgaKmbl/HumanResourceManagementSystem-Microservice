package com.tolgakumbul.authservice.entity;

public enum AuthenticationStatus {
    ACTIVE,      // Current active token
    EXPIRED,     // Token has expired
    INVALIDATED  // Token is invalidated (e.g., due to manual logout)
}