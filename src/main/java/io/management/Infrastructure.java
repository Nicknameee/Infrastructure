package io.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class Infrastructure {
    public static void main(String[] args) {
        SpringApplication.run(Infrastructure.class);
        log.debug("Infrastructure service started");
    }
}