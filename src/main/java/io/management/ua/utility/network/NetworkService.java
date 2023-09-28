package io.management.ua.utility.network;

import io.management.ua.utility.UtilManager;
import io.management.ua.utility.models.NetworkResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NetworkService {
    public NetworkResponse performRequest(@NotNull HttpMethod httpMethod,
                                          @NotNull String url,
                                          @NotNull Map<String, String> headers,
                                          @Nullable Object body) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(ChronoUnit.SECONDS.getDuration().multipliedBy(5))
                .build();

        addDefaultHeaders(headers);

        List<String> headerList = new ArrayList<>();
        headers.forEach((key, value) -> {
            headerList.add(key);
            headerList.add(value);
        });
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .headers(headerList.toArray(new String[0]))
                .method(httpMethod.name(), HttpRequest.BodyPublishers
                        .ofString(UtilManager.objectMapper()
                                .writeValueAsString(body)))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return NetworkResponse.builder()
                .httpStatus(HttpStatus.valueOf(response.statusCode()))
                .headers(response.headers())
                .body(response.body())
                .build();
    }

    private void addDefaultHeaders(Map<String, String> headers) {
        headers.put(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
    }
}