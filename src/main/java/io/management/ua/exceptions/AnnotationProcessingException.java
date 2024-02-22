package io.management.ua.exceptions;

import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class AnnotationProcessingException extends GlobalException {
    public AnnotationProcessingException(HttpStatus httpStatus, ZonedDateTime exceptionTime, String exception) {
        super(httpStatus, exceptionTime, exception);
    }

    public AnnotationProcessingException(String exception) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), exception);
    }
}
