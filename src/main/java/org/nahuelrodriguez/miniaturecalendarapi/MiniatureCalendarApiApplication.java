package org.nahuelrodriguez.miniaturecalendarapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class MiniatureCalendarApiApplication {
    public static void main(final String[] args) {
        SpringApplication.run(MiniatureCalendarApiApplication.class, args);
    }
}
