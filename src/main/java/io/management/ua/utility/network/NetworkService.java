package io.management.ua.utility.network;

import io.management.ua.annotations.Export;
import io.management.ua.utility.UtilManager;
import io.management.ua.utility.models.NetworkResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NetworkService {
    @Export
    public NetworkResponse performRequest(@NotNull String url) throws URISyntaxException, IOException, InterruptedException {
        return performRequest(HttpMethod.GET, url, new HashMap<>(), null);
    }

    @Export
    public NetworkResponse performRequest(@NotNull HttpMethod httpMethod,
                                          @NotNull String url) throws URISyntaxException, IOException, InterruptedException {
        return performRequest(httpMethod, url, new HashMap<>(), null);
    }

    @Export
    public NetworkResponse performRequest(@NotNull HttpMethod httpMethod,
                                          @NotNull String url,
                                          @NotNull Map<String, String> headers) throws URISyntaxException, IOException, InterruptedException {
        return performRequest(httpMethod, url, headers, null);
    }

    @Export
    public NetworkResponse performRequest(@NotNull HttpMethod httpMethod,
                                          @NotNull String url,
                                          @NotNull Map<String, String> headers,
                                          @Nullable Object body) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(ChronoUnit.SECONDS.getDuration().multipliedBy(5))
                .build();

        addDefaultHeaders(headers);

        List<String> headerList = new ArrayList<>();
        headers.forEach((key, value) -> {
            headerList.add(key);
            headerList.add(value);
        });

        HttpRequest httpRequest;

        if (body != null) {
            if (body instanceof ByteArrayOutputStream) {
                httpRequest = HttpRequest.newBuilder(new URI(url))
                        .headers(headerList.toArray(new String[0]))
                        .method(httpMethod.name(), HttpRequest.BodyPublishers
                                .ofByteArray(((ByteArrayOutputStream) body).toByteArray()))
                        .build();
            } else if (body instanceof String) {
                if (headers.get(HttpHeaders.CONTENT_TYPE).equals(MediaType.APPLICATION_XML_VALUE)) {
                    httpRequest = HttpRequest.newBuilder(new URI(url))
                            .headers(headerList.toArray(new String[0]))
                            .method(httpMethod.name(), HttpRequest.BodyPublishers
                                    .ofByteArray(((String) body).getBytes(StandardCharsets.UTF_8)))
                            .build();
                } else {
                    httpRequest = HttpRequest.newBuilder(new URI(url))
                            .headers(headerList.toArray(new String[0]))
                            .method(httpMethod.name(), HttpRequest.BodyPublishers
                                    .ofString((String) body))
                            .build();
                }
            } else {
                httpRequest = HttpRequest.newBuilder(new URI(url))
                        .headers(headerList.toArray(new String[0]))
                        .method(httpMethod.name(), HttpRequest.BodyPublishers
                                .ofString(UtilManager.objectMapper()
                                        .writeValueAsString(body)))
                        .build();
            }
        } else {
            httpRequest = HttpRequest.newBuilder(new URI(url))
                    .headers(headerList.toArray(new String[0]))
                    .method(httpMethod.name(), HttpRequest.BodyPublishers.noBody())
                    .build();
        }

        log.debug("Performing HTTP {} request, url {}, headers {}, body {}", httpMethod, url, headers, body);

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        log.debug("Response with status {}, headers {}, body {}", HttpStatus.valueOf(response.statusCode()), response.headers(), response.body().substring(0, Math.min(100, response.body().length())) + "...");

        return NetworkResponse.builder()
                .httpStatus(HttpStatus.valueOf(response.statusCode()))
                .headers(response.headers())
                .body(response.body())
                .build();
    }

    private void addDefaultHeaders(Map<String, String> headers) {
        headers.put(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}
