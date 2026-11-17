package com.example.sampleapi.dto;

import java.time.Instant;

/**
 * Payload returned by {@code GET /api/v1/health}.
 *
 * @param status        "UP" when the service is able to serve requests
 * @param service       configured service name
 * @param version       configured service version
 * @param uptimeSeconds seconds since the application context started
 * @param timestamp     server time when the response was produced
 */
public record HealthResponse(
        String status,
        String service,
        String version,
        long uptimeSeconds,
        Instant timestamp) {
}
