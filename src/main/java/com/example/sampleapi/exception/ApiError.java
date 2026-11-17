package com.example.sampleapi.exception;

import java.time.Instant;
import java.util.List;

/**
 * Uniform error payload returned by the global exception handler.
 *
 * @param status    HTTP status code
 * @param error     short error label
 * @param message   human readable explanation
 * @param details   per-field validation messages, empty for non-validation errors
 * @param path      request path that failed
 * @param timestamp server time of the failure
 */
public record ApiError(
        int status,
        String error,
        String message,
        List<String> details,
        String path,
        Instant timestamp) {

    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(status, error, message, List.of(), path, Instant.now());
    }
}
