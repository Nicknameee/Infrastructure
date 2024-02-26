package io.management.resources.service;

import com.fasterxml.jackson.core.type.TypeReference;
import io.management.resources.dto.CloudinaryUploadResponse;
import io.management.resources.mapper.ImageMapper;
import io.management.resources.models.Image;
import io.management.resources.repository.ImageRepository;
import io.management.ua.annotations.Export;
import io.management.ua.utility.UtilManager;
import io.management.ua.utility.models.NetworkResponse;
import io.management.ua.utility.network.NetworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageHostingService {
    private final ImageRepository imageRepository;
    private final NetworkService networkService;
    private final ImageMapper imageMapper;

    @Value("${api.resources.images.host}")
    private String host;
    @Value("${api.resources.images.key}")
    private String key;
    @Value("${api.resources.images.secret}")
    private String secret;

    @Export
    public Image uploadImage(MultipartFile file) {
        Map<String, String> headers = new HashMap<>();
        String bound = UUID.randomUUID().toString().replaceAll("-", "");
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE + ";boundary=" + bound);

        try {
            NetworkResponse networkResponse =
                    networkService.performRequest(HttpMethod.POST, host + "/upload", headers, writeFormDataBody(file, bound));

            if (networkResponse.getHttpStatus() == HttpStatus.OK) {
                CloudinaryUploadResponse response =
                        UtilManager.objectMapper().readValue((String) networkResponse.getBody(),
                                new TypeReference<>(){});
                Image image = imageMapper.responseToImage(response);
                return imageRepository.save(image);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return null;
    }

    private String generateSignature(String publicId, long timestamp) {
        String dataToHash = String.format("public_id=%s&timestamp=%d%s", publicId, timestamp, secret);
        return DigestUtils.sha256Hex(dataToHash);
    }

    private ByteArrayOutputStream writeFormDataBody(MultipartFile file, String bound) throws IOException {
        if (file == null || file.getOriginalFilename() == null) {
            throw new RuntimeException("Invalid file data passed");
        }

        try (InputStream fileInputStream = file.getInputStream()) {
            byte[] imageContent = fileInputStream.readAllBytes();
            long timestamp = Instant.now().toEpochMilli();
            String filePart = String.format("Content-Disposition: form-data; name=\"file\"; filename=\"%s\"\r\nContent-Type: image/jpeg\r\n\r\n", file.getOriginalFilename());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            outputStream.write(String.format("--%s\r\n", bound).getBytes());
            outputStream.write("Content-Disposition: form-data; name=\"public_id\"\r\n\r\n".getBytes());
            outputStream.write(file.getOriginalFilename().getBytes());
            outputStream.write("\r\n".getBytes());

            outputStream.write(String.format("--%s\r\n", bound).getBytes());
            outputStream.write("Content-Disposition: form-data; name=\"signature\"\r\n\r\n".getBytes());
            outputStream.write(generateSignature(file.getOriginalFilename(), timestamp).getBytes());
            outputStream.write("\r\n".getBytes());

            outputStream.write(String.format("--%s\r\n", bound).getBytes());
            outputStream.write("Content-Disposition: form-data; name=\"api_key\"\r\n\r\n".getBytes());
            outputStream.write(key.getBytes());
            outputStream.write("\r\n".getBytes());

            outputStream.write(String.format("--%s\r\n", bound).getBytes());
            outputStream.write("Content-Disposition: form-data; name=\"timestamp\"\r\n\r\n".getBytes());
            outputStream.write(String.valueOf(timestamp).getBytes());
            outputStream.write("\r\n".getBytes());

            outputStream.write(String.format("--%s\r\n", bound).getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(filePart.getBytes());
            outputStream.write(imageContent);

            outputStream.write(("\r\n--" + bound + "--\r\n").getBytes());

            return outputStream;
        }
    }

    private ByteArrayOutputStream writeFormDataBody(String publicId, String bound) throws IOException {
        long timestamp = Instant.now().toEpochMilli();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(String.format("--%s\r\n", bound).getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"public_id\"\r\n\r\n".getBytes());
        outputStream.write(publicId.getBytes());
        outputStream.write("\r\n".getBytes());

        outputStream.write(String.format("--%s\r\n", bound).getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"signature\"\r\n\r\n".getBytes());
        outputStream.write(generateSignature(publicId, timestamp).getBytes());
        outputStream.write("\r\n".getBytes());

        outputStream.write(String.format("--%s\r\n", bound).getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"api_key\"\r\n\r\n".getBytes());
        outputStream.write(key.getBytes());
        outputStream.write("\r\n".getBytes());

        outputStream.write(String.format("--%s\r\n", bound).getBytes());
        outputStream.write("Content-Disposition: form-data; name=\"timestamp\"\r\n\r\n".getBytes());
        outputStream.write(String.valueOf(timestamp).getBytes());

        outputStream.write(("\r\n--" + bound + "--\r\n").getBytes());

        return outputStream;
    }

    @Export
    public boolean deleteImage(String name) {
        Map<String, String> headers = new HashMap<>();
        String bound = UUID.randomUUID().toString().replaceAll("-", "");
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE + ";boundary=" + bound);

        try {
            NetworkResponse networkResponse =
                    networkService.performRequest(HttpMethod.POST, host + "/destroy", headers, writeFormDataBody(name, bound));

            if (networkResponse.getHttpStatus() == HttpStatus.OK) {
                String response = UtilManager.objectMapper()
                        .readTree((String) networkResponse.getBody())
                        .get("result")
                        .asText();

                if (response.equalsIgnoreCase("OK")) {
                    imageRepository.deleteById(name);

                    return true;
                } else {
                    log.debug("Cloudinary image destroying response {}", response);

                    return false;
                }

            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return false;
    }

    @Export
    public String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
