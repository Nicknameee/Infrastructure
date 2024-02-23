package io.management.ua.exceptions;

import io.management.ua.utility.Pattern;
import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

public class AnnotationProcessingException extends GlobalException {
    public AnnotationProcessingException(String exception) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), exception);
    }

    public AnnotationProcessingException(String exception, Object ... args) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), Pattern.renderException(exception, args));
    }
}
