package io.management.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CloudinaryUploadResponse implements Serializable {
    @JsonProperty("asset_id")
    private String assetId;
    @JsonProperty("public_id")
    private String publicId;
    @JsonProperty("version")
    private long version;
    @JsonProperty("version_id")
    private String versionId;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;
    @JsonProperty("format")
    private String format;
    @JsonProperty("resource_type")
    private String resourceType;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("tags")
    private List<String> tags;
    @JsonProperty("bytes")
    private long bytes;
    @JsonProperty("type")
    private String type;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("placeholder")
    private boolean placeholder;
    @JsonProperty("url")
    private String url;
    @JsonProperty("secure_url")
    private String secureUrl;
    @JsonProperty("folder")
    private String folder;
    @JsonProperty("access_mode")
    private String accessMode;
    @JsonProperty("overwritten")
    private boolean overwritten;
    @JsonProperty("original_filename")
    private String originalFilename;
    @JsonProperty("original_extension")
    private String originalExtension;
}
