package com.example.sampleapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Binds the {@code app.*} section of application.yml.
 *
 * @param name    human readable service name
 * @param version service version reported by the health endpoint
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(String name, String version) {
}
