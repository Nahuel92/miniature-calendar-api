package org.nahuelrodriguez.miniaturecalendarapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniatureCalendarApiApplication {
    public static void main(final String[] args) {
        SpringApplication.run(MiniatureCalendarApiApplication.class, args);
    }
}
