package com.example.sampleapi.service;

import java.time.Duration;
import java.time.Instant;
import org.springframework.stereotype.Service;

/**
 * Tracks how long the current application instance has been running.
 */
@Service
public class UptimeService {

    private final Instant startedAt = Instant.now();

    public Instant startedAt() {
        return startedAt;
    }

    public long uptimeSeconds() {
        return Duration.between(startedAt, Instant.now()).toSeconds();
    }
}
