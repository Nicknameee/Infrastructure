package io.management.ua.response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ResponseSerializer extends JsonSerializer<Response<?>> {
    @Override
    public void serialize(Response<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("data");
        gen.writeObject(value.getData());
        gen.writeFieldName("status");
        gen.writeObject(value.getHttpStatus());
        gen.writeEndObject();
    }
}
