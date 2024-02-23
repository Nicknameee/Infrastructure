package io.management.ua.exceptions;

import io.management.ua.utility.Pattern;
import io.management.ua.utility.TimeUtil;
import org.springframework.http.HttpStatus;

public class BeanProcessingException extends GlobalException {
    public BeanProcessingException(String exception) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, TimeUtil.getCurrentDateTime(), exception);
    }

    public BeanProcessingException(String exception, Object ... args) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, TimeUtil.getCurrentDateTime(), Pattern.renderException(exception, args));
    }
}
