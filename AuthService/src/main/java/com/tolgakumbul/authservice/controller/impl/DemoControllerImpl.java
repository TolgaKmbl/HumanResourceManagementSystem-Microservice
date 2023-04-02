package com.tolgakumbul.authservice.controller.impl;

import com.tolgakumbul.authservice.controller.DemoController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoControllerImpl implements DemoController {

    @Override
    public ResponseEntity<String> testSecuredApi() {
        return ResponseEntity.ok("You have reached the secured api with Bearer token");
    }
}
