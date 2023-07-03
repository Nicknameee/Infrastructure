package io.management.ua.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class AuthenticationException extends GlobalException {
    public AuthenticationException(HttpStatus httpStatus, LocalDateTime exceptionTime, String exception) {
        super(httpStatus, exceptionTime, exception);
    }
}
