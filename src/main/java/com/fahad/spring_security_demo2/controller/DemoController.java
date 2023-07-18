package com.fahad.spring_security_demo2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> demoEndPoint() {
        return ResponseEntity.ok("Hello bro from secured endpoint");
    }
}
