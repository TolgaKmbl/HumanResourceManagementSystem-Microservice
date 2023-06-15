package com.tolgakumbul.authservice.controller.impl;

import com.tolgakumbul.authservice.controller.DemoController;
import com.tolgakumbul.authservice.core.Result;
import com.tolgakumbul.authservice.core.SuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoControllerImpl implements DemoController {

    @Override
    public ResponseEntity<Result> testSecuredApi() {
        return ResponseEntity.ok(new SuccessResult("You have reached the secured api with Bearer token"));
    }
}
