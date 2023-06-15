package com.tolgakumbul.authservice.controller;

import com.tolgakumbul.authservice.core.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/demo/v0")
public interface DemoController {

    @GetMapping("/test")
    ResponseEntity<Result> testSecuredApi();
}
