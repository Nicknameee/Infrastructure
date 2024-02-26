package io.management.resources.mapper;

import io.management.resources.dto.CloudinaryUploadResponse;
import io.management.resources.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {
    Image responseToImage(CloudinaryUploadResponse response);
}
