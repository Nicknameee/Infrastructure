package io.management.ua.exceptions;

import io.management.ua.utility.Pattern;
import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

public class NotFoundException extends GlobalException {
    public NotFoundException(String exception) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), exception);
    }

    public NotFoundException(String exception, Object ... args) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), Pattern.renderException(exception, args));
    }
}
