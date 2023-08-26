package io.management.ua.exceptions;

import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ActionRestrictedException extends GlobalException {
    public ActionRestrictedException(HttpStatus httpStatus, ZonedDateTime exceptionTime, String exception) {
        super(httpStatus, exceptionTime, exception);
    }

    public ActionRestrictedException(String exception) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), exception);
    }
}
