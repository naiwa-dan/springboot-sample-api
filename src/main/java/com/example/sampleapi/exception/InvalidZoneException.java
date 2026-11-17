package com.example.sampleapi.exception;

/**
 * Thrown when the caller supplies a time zone id that {@link java.time.ZoneId} cannot resolve.
 */
public class InvalidZoneException extends RuntimeException {

    private final String zone;

    public InvalidZoneException(String zone) {
        super("unknown time zone: " + zone);
        this.zone = zone;
    }

    public String getZone() {
        return zone;
    }
}
