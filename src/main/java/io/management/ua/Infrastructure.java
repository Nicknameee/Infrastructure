package io.management.ua;

import io.management.ua.annotations.Value;
import javassist.bytecode.stackmap.TypeData;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class Infrastructure    {
    public static void main(String[] args) {
        log.debug("Infrastructure service started");
        SpringApplication.run(Infrastructure.class);
    }

    @Service
    public static class AmasingSuckCuckSumshotInAnusAnalAchkoAxuetitPenisConcha{
        @Value(property = "spring.kafka.bootstrap-servers")
        private String name;

        @PostConstruct
        public void suckCuckFuckingSlaveRightNow() {
            System.out.println(TypeData.ClassName.class);
            System.out.println(name
            );
            JsonUtils.class.getName();
        }
    }
}