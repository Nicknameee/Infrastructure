package io.management.ua.utility.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.http.HttpHeaders;

@Data
@Builder
public class NetworkResponse {
    private HttpStatus httpStatus;
    private HttpHeaders headers;
    private Object body;
}
