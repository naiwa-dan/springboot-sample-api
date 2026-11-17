package com.example.sampleapi.dto;

/**
 * Payload returned by {@code GET /api/v1/time}.
 *
 * @param zone         resolved time zone id
 * @param iso          ISO-8601 representation of the current time in that zone
 * @param formatted    human readable representation (yyyy-MM-dd HH:mm:ss)
 * @param epochMillis current time as epoch milliseconds
 * @param offset       UTC offset of the zone, for example "+08:00"
 */
public record TimeResponse(
        String zone,
        String iso,
        String formatted,
        long epochMillis,
        String offset) {
}
