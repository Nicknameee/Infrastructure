package io.management.ua.handlers;

import io.management.ua.exceptions.GlobalException;
import io.management.ua.utility.TimeUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleApplicationExceptions(Exception e) {
        if (e instanceof GlobalException exception) {
            return ResponseEntity.status(exception.getHttpStatus()).body(exception);
        } else if (e instanceof ConstraintViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalException
                    .builder()
                    .exceptionTime(TimeUtil.getCurrentDateTime())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .exception(exception
                            .getConstraintViolations()
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .toList()
                            .toString()));
        } else if (e instanceof MethodArgumentNotValidException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalException
                    .builder()
                    .exceptionTime(TimeUtil.getCurrentDateTime())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .exception(exception
                            .getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .filter(Objects::nonNull)
                            .toList()
                            .toString()));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }
}
