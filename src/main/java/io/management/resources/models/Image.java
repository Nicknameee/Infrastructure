package io.management.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.management.resources.converters.StringListConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @Column(name = "public_id")
    @JsonProperty("public_id")
    private String publicId;
    @Column(name = "asset_id")
    private String assetId;
    @Column(name = "version")
    private Long version;
    @Column(name = "version_id")
    private String versionId;
    @Column(name = "signature")
    private String signature;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;
    @Column(name = "format")
    private String format;
    @Column(name = "resource_type")
    private String resourceType;
    @Column(name = "created_at")
    private String createdAt;
    @Convert(converter = StringListConverter.class)
    @Column(name = "tags")
    private List<String> tags;
    @Column(name = "bytes")
    private Long bytes;
    @Column(name = "type")
    private String type;
    @Column(name = "etag")
    private String etag;
    @Column(name = "placeholder")
    private Boolean placeholder;
    @Column(name = "url")
    private String url;
    @Column(name = "secure_url")
    private String secureUrl;
    @Column(name = "folder")
    private String folder;
    @Column(name = "access_mode")
    private String accessMode;
    @Column(name = "overwritten")
    private Boolean overwritten;
    @Column(name = "original_filename")
    private String originalFilename;
    @Column(name = "original_extension")
    private String originalExtension;
}
