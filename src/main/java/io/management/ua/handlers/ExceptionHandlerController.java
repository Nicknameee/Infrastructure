package io.management.ua.handlers;

import io.management.ua.exceptions.GlobalException;
import io.management.ua.response.Response;
import io.management.ua.utility.TimeUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public Response<?> handleApplicationExceptions(Exception e) {
        if (e instanceof GlobalException exception) {
            return Response.of(exception.getHttpStatus()).exception(exception);
        } else if (e instanceof ConstraintViolationException exception) {
            return Response
                    .of(HttpStatus.BAD_REQUEST)
                    .exception(GlobalException
                            .builder()
                            .exceptionTime(TimeUtil.getCurrentDateTime())
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .exception(exception
                                    .getConstraintViolations()
                                    .stream()
                                    .map(constraintViolation -> String.format("%s '%s' %s",
                                            constraintViolation.getPropertyPath(),
                                            constraintViolation.getInvalidValue(),
                                            constraintViolation.getMessage())
                                    )
                                    .toList()
                                    .toString())
                            .build());
        } else if (e instanceof MethodArgumentNotValidException exception) {
            return Response
                    .of(HttpStatus.BAD_REQUEST)
                    .exception(GlobalException
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
                                    .toString())
                            .build());
        }

        return Response.of(HttpStatus.INTERNAL_SERVER_ERROR).exception(GlobalException
                .builder()
                .exception(e.getMessage())
                .build());
    }
}
