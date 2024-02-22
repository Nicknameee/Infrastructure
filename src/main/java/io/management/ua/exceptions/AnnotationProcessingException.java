package io.management.ua.exceptions;

import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

public class AnnotationProcessingException extends GlobalException {
    public AnnotationProcessingException(String exception) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), exception);
    }
}
