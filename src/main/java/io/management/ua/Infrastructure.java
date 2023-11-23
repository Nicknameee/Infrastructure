package io.management.ua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class Infrastructure {
    public static void main(String[] args) {
        log.debug("Infrastructure service started");
        SpringApplication.run(Infrastructure.class);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @PostConstruct
    public void consrt() {
        kafkaTemplate.send("service", "CSCSC");
    }
}