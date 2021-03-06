package org.gymanager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/health")
    public String getHealthCheck() {
        return appName;
    }
}