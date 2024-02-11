package io.management.ua.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonSerialize(using = ResponseSerializer.class)
public class Response<T> {
    private String exception;
    private T data;
    private HttpStatus httpStatus;
    private static Response<?> instance;

    private Response() {
        this.httpStatus = HttpStatus.OK;
    }

    private Response(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private Response(T data) {
        this.httpStatus = HttpStatus.OK;
        this.data = data;
    }

    public static Response<?> ok() {
        return new Response<>();
    }

    public static Response<?> ok(Object data) {
        return new Response<>(data);
    }

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

    public Response<?> build() {
        Response<?> response = instance;
        instance = null;

        return response;
    }

    public Response<?> exception(String exception) {
        instance.setException(exception);

        return instance;
    }
}
