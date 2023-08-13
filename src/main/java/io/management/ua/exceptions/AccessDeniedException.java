package io.management.ua.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class AccessDeniedException extends GlobalException {
    public AccessDeniedException(HttpStatus httpStatus, ZonedDateTime exceptionTime, String exception) {
        super(httpStatus, exceptionTime, exception);
    }
}
