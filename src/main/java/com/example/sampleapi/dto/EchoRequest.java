package com.example.sampleapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body for {@code POST /api/v1/echo}.
 *
 * @param message payload to echo back, 1..500 characters
 * @param client  optional caller name used for logging and the response
 */
public record EchoRequest(
        @NotBlank(message = "message must not be blank")
        @Size(max = 500, message = "message must be at most 500 characters")
        String message,

        @Size(max = 64, message = "client must be at most 64 characters")
        String client) {
}
