package io.management.ua.exceptions;

import io.management.ua.utility.Pattern;
import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends GlobalException {
    public AuthenticationException(HttpStatus httpStatus, String exception) {
        super(httpStatus, TimeUtil.getCurrentDateTime(), exception);
    }

    public AuthenticationException(String exception) {
        super(HttpStatus.UNAUTHORIZED, TimeUtil.getCurrentDateTime(), exception);
    }

    public AuthenticationException(String exception, Object ... args) {
        super(HttpStatus.UNAUTHORIZED, TimeUtil.getCurrentDateTime(), Pattern.renderException(exception, args));
    }
}
