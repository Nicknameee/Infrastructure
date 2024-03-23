package io.management.ua.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.management.ua.annotations.Export;
import io.management.ua.exceptions.GlobalException;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonSerialize(using = ResponseSerializer.class)
public class Response<T> {
    private GlobalException exception;
    private T data;
    private HttpStatus httpStatus;
    private static Response<?> instance;

    private Response() {
        this.httpStatus = HttpStatus.OK;
    }

    private Response(T data) {
        this.httpStatus = HttpStatus.OK;
        this.data = data;
    }

    @Export
    public static Response<?> ok() {
        return new Response<>();
    }

    @Export
    public static Response<?> ok(Object data) {
        return new Response<>(data);
    }

    @Export
    public static Response<?> of(HttpStatus httpStatus) {
        if (instance == null) {
            instance = new Response<>();
            instance.setHttpStatus(httpStatus);
        } else {
            Object existingData = instance.getData();
            instance = new Response<>(existingData);
            instance.setHttpStatus(httpStatus);
        }

        return instance;
    }

    @Export
    public Response<?> data(Object data) {
        if (instance == null) {
            instance = new Response<>(data);
        } else {
            HttpStatus existingStatus = instance.getHttpStatus();
            instance = new Response<>(data);
            instance.setHttpStatus(existingStatus);
        }

        return instance;
    }

    @Export
    public Response<?> build() {
        Response<?> response = instance;
        instance = null;

        return response;
    }

    @Export
    public Response<?> exception(GlobalException exception) {
        instance.setException(exception);

        return instance;
    }
}
