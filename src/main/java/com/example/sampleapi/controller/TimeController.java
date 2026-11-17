package com.example.sampleapi.controller;

import com.example.sampleapi.dto.TimeResponse;
import com.example.sampleapi.exception.InvalidZoneException;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint 3 of 3: returns the current server time for a requested time zone.
 */
@RestController
@RequestMapping("/api/v1")
public class TimeController {

    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Clock clock;

    public TimeController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping("/time")
    public TimeResponse time(@RequestParam(name = "zone", defaultValue = "UTC") String zone) {
        ZoneId zoneId = resolveZone(zone);
        ZonedDateTime now = ZonedDateTime.now(clock).withZoneSameInstant(zoneId);

        return new TimeResponse(
                zoneId.getId(),
                now.toOffsetDateTime().toString(),
                now.format(PRETTY),
                now.toInstant().toEpochMilli(),
                now.getOffset().getId());
    }

    private ZoneId resolveZone(String zone) {
        try {
            return ZoneId.of(zone);
        } catch (DateTimeException ex) {
            throw new InvalidZoneException(zone);
        }
    }
}
