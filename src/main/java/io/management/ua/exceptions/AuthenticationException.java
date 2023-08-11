package io.management.ua.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class AuthenticationException extends GlobalException {
    public AuthenticationException(HttpStatus httpStatus, ZonedDateTime exceptionTime, String exception) {
        super(httpStatus, exceptionTime, exception);
    }
}
