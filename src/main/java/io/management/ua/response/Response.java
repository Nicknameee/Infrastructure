package io.management.ua.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@JsonSerialize(using = ResponseSerializer.class)
public class Response<T> {
    private T data;
    private HttpStatus httpStatus;
    private static Response<?> instance;

    private Response(T data) {
        this.data = data;
    }

    public static Response<?> ok() {
        return new Response<>();
    }
    public static Response<?> ok(Object data) {
        Response<?> instance = new Response<>(data);
        instance.setHttpStatus(HttpStatus.OK);

        return instance;
    }

    public static void of(HttpStatus httpStatus) {
        if (instance == null) {
            instance = new Response<>();
            instance.setHttpStatus(httpStatus);
        } else {
            Object existingData = instance.getData();
            instance = new Response<>(existingData);
            instance.setHttpStatus(httpStatus);
        }

    }

    public static void data(Object data) {
        if (instance == null) {
            instance = new Response<>(data);
        } else {
            HttpStatus existingStatus = instance.getHttpStatus();
            instance = new Response<>(data);
            instance.setHttpStatus(existingStatus);
        }
    }

    public static Response<?> build() {
        Response<?> response = instance;
        instance = null;

        return response;
    }
}
