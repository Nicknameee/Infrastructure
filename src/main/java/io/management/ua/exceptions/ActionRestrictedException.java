package io.management.ua.exceptions;

import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ActionRestrictedException extends GlobalException {
    public ActionRestrictedException(String exception) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), exception);
    }
}
