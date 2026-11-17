package com.example.sampleapi.dto;

import java.time.Instant;

/**
 * Payload returned by {@code POST /api/v1/echo}.
 *
 * @param message    the original message that was received
 * @param length     message length in characters
 * @param client     caller name, defaults to "anonymous" when omitted
 * @param receivedAt server time when the message was accepted
 */
public record EchoResponse(
        String message,
        int length,
        String client,
        Instant receivedAt) {
}
