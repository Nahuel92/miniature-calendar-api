package org.nahuelrodriguez.miniaturecalendarapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApiDateUtils {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");
    private static final String FIRST_PUBLISHED_PICTURE_DATE = "110420";
    public static final LocalDate START_DATE = LocalDate.parse(FIRST_PUBLISHED_PICTURE_DATE, DATE_FORMAT);
}
