package io.management.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryDestroyRequest {
    @JsonProperty("public_id")
    private String publicId;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("timestamp")
    private String timestamp;
}
