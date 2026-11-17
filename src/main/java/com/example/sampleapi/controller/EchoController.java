package com.example.sampleapi.controller;

import com.example.sampleapi.dto.EchoRequest;
import com.example.sampleapi.dto.EchoResponse;
import jakarta.validation.Valid;
import java.time.Instant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint 2 of 3: echoes a validated JSON payload back to the caller.
 */
@RestController
@RequestMapping("/api/v1")
public class EchoController {

    private static final String ANONYMOUS = "anonymous";

    @PostMapping("/echo")
    public EchoResponse echo(@Valid @RequestBody EchoRequest request) {
        String client = request.client() == null || request.client().isBlank()
                ? ANONYMOUS
                : request.client().trim();

        return new EchoResponse(
                request.message(),
                request.message().length(),
                client,
                Instant.now());
    }
}
