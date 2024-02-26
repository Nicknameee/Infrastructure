package io.management.ua.exceptions;

import io.management.ua.utility.Pattern;
import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class DefaultException extends GlobalException {
    public DefaultException(String exception, Object ... args) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), Pattern.renderException(exception, args));
    }
    public DefaultException(HttpStatus httpStatus, ZonedDateTime exceptionTime, String exception) {
        super(httpStatus, exceptionTime, exception);
    }
}
