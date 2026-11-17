package com.example.sampleapi.controller;

import com.example.sampleapi.config.AppProperties;
import com.example.sampleapi.dto.HealthResponse;
import com.example.sampleapi.service.UptimeService;
import java.time.Instant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint 1 of 3: liveness style health check.
 */
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    private final AppProperties appProperties;
    private final UptimeService uptimeService;

    public HealthController(AppProperties appProperties, UptimeService uptimeService) {
        this.appProperties = appProperties;
        this.uptimeService = uptimeService;
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse(
                "UP",
                appProperties.name(),
                appProperties.version(),
                uptimeService.uptimeSeconds(),
                Instant.now());
    }
}
