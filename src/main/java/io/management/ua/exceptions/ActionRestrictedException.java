package io.management.ua.exceptions;

import io.management.ua.utility.Pattern;
import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

public class ActionRestrictedException extends GlobalException {
    public ActionRestrictedException(String exception) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), exception);
    }

    public ActionRestrictedException(String exception, Object ... args) {
        super(HttpStatus.CONFLICT, TimeUtil.getCurrentDateTime(), Pattern.renderException(exception, args));
    }
}
