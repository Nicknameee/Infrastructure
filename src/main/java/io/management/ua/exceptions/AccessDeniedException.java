package io.management.ua.exceptions;

import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends GlobalException {
    public AccessDeniedException(String exception) {
        super(HttpStatus.FORBIDDEN, TimeUtil.getCurrentDateTime(), exception);
    }   public AccessDeniedException(HttpStatus httpStatus, String exception) {
        super(httpStatus, TimeUtil.getCurrentDateTime(), exception);
    }
}
