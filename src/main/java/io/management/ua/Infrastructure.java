package io.management.ua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Infrastructure    {
    public static void main(String[] args) {
        log.debug("Infrastructure service started");
        SpringApplication.run(Infrastructure.class);
    }
}