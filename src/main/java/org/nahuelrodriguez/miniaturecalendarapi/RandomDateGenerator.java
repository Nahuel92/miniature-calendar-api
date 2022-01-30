package org.nahuelrodriguez.miniaturecalendarapi;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomDateGenerator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");
    private static final String FIRST_PICTURE_DATE = "110420";

    public String getDate() {
        final var start = LocalDate.parse(FIRST_PICTURE_DATE, DATE_FORMAT);
        final var today = LocalDate.now().plusDays(1);
        final var randomDate = ThreadLocalRandom.current()
                .nextLong(start.toEpochDay(), today.toEpochDay());
        return LocalDate.ofEpochDay(randomDate).format(DATE_FORMAT);
    }
}
