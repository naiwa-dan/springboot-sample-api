package com.example.sampleapi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApiEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/health reports the service as UP")
    void healthReportsUp() throws Exception {
        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("SpringBoot Sample API"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.uptimeSeconds").isNumber())
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("POST /api/v1/echo returns the message, its length and the client")
    void echoReturnsPayload() throws Exception {
        String body = """
                {"message": "hello spring", "client": "junit"}
                """;

        mockMvc.perform(post("/api/v1/echo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("hello spring"))
                .andExpect(jsonPath("$.length").value(12))
                .andExpect(jsonPath("$.client").value("junit"))
                .andExpect(jsonPath("$.receivedAt", notNullValue()));
    }

    @Test
    @DisplayName("POST /api/v1/echo falls back to anonymous and rejects a blank message")
    void echoValidatesInput() throws Exception {
        mockMvc.perform(post("/api/v1/echo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"  \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.details[0]", containsString("message")));
    }

    @Test
    @DisplayName("GET /api/v1/time defaults to UTC")
    void timeDefaultsToUtc() throws Exception {
        mockMvc.perform(get("/api/v1/time"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zone").value("UTC"))
                .andExpect(jsonPath("$.offset").value("Z"))
                .andExpect(jsonPath("$.epochMillis").isNumber());
    }

    @Test
    @DisplayName("GET /api/v1/time resolves an IANA zone")
    void timeResolvesZone() throws Exception {
        mockMvc.perform(get("/api/v1/time").param("zone", "Asia/Shanghai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zone").value("Asia/Shanghai"))
                .andExpect(jsonPath("$.offset").value("+08:00"));
    }

    @Test
    @DisplayName("GET /api/v1/time rejects an unknown zone")
    void timeRejectsUnknownZone() throws Exception {
        mockMvc.perform(get("/api/v1/time").param("zone", "Mars/Olympus"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("INVALID_ZONE"));
    }

    @Test
    @DisplayName("unknown paths return the JSON 404 payload")
    void unknownPathReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/nope"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
