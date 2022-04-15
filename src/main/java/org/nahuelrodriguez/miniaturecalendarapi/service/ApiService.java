package org.nahuelrodriguez.miniaturecalendarapi.service;

import org.nahuelrodriguez.miniaturecalendarapi.utils.ApiDateUtils;
import org.nahuelrodriguez.miniaturecalendarapi.entity.Picture;
import org.nahuelrodriguez.miniaturecalendarapi.validator.DateValidator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ApiService {
    private final DateValidator dateValidator;
    private final PicturesCrawler picturesCrawler;

    ApiService(final DateValidator dateValidator, final PicturesCrawler picturesCrawler) {
        this.dateValidator = dateValidator;
        this.picturesCrawler = picturesCrawler;
    }

    @Cacheable(value = "picturesFromDate", key = "#date", unless = "#result == null")
    public Collection<Picture> getPicturesFromDate(final Locale locale, final String date) {
        dateValidator.validateFormat(date, locale);
        return picturesCrawler.getPicturesForDate(date);
    }

    public Collection<Picture> getPicturesOfTheDay(final Locale locale) {
        final var today = LocalDate.now().format(ApiDateUtils.DATE_FORMAT);
        return getPicturesFromDate(locale, today);
    }

    public Collection<Picture> getPicturesFromRandomDay(final Locale locale) {
        return getPicturesFromDate(locale, calculateRandomDate());
    }

    private String calculateRandomDate() {
        final var tomorrow = LocalDate.now().plusDays(1);
        final var randomDate = ThreadLocalRandom.current()
                .nextLong(ApiDateUtils.START_DATE.toEpochDay(), tomorrow.toEpochDay());
        return LocalDate.ofEpochDay(randomDate).format(ApiDateUtils.DATE_FORMAT);
    }
}
