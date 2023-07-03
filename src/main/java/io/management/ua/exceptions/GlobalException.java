package io.management.ua.exceptions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonSerialize(using = GlobalException.GlobalExceptionSerializer.class)
public class GlobalException extends Exception {
    private HttpStatus httpStatus;
    private LocalDateTime exceptionTime;
    private String exception;

    static class GlobalExceptionSerializer extends JsonSerializer<GlobalException> {

        @Override
        public void serialize(GlobalException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            ObjectNode objectNode = new ObjectMapper().createObjectNode();
            objectNode.put("httpStatus", e.getHttpStatus().name());
            objectNode.put("exceptionTime", e.getExceptionTime().toString());
            objectNode.put("exception", e.getException());
            jsonGenerator.writeTree(objectNode);
        }
    }
}
