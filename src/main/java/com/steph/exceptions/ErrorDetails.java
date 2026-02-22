package com.steph.exceptions;

public record ErrorDetails(
        java.time.LocalDateTime now,
        String message,
        String details,
        String errorCode
) {
}
