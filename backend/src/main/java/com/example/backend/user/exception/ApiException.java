package com.example.backend.user.exception;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Data
@Builder
public class ApiException {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
}
