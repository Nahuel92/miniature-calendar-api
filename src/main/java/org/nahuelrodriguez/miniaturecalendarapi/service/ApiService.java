package org.nahuelrodriguez.miniaturecalendarapi.service;

import org.nahuelrodriguez.miniaturecalendarapi.entity.Picture;
import org.nahuelrodriguez.miniaturecalendarapi.utils.ApiDateUtils;
import org.nahuelrodriguez.miniaturecalendarapi.validator.DateValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ApiService {
    private final DateValidator dateValidator;
    private final PicturesCrawler picturesCrawler;

    ApiService(final DateValidator dateValidator, final PicturesCrawler picturesCrawler) {
        this.dateValidator = dateValidator;
        this.picturesCrawler = picturesCrawler;
    }

    public Collection<Picture> getPicturesForDate(final Locale locale, final String date) {
        dateValidator.validateFormat(date, locale);
        return picturesCrawler.getPicturesForDate(date);
    }

    public Collection<Picture> getPicturesForToday(final Locale locale) {
        final var today = LocalDate.now().format(ApiDateUtils.DATE_FORMAT);
        return getPicturesForDate(locale, today);
    }

    public Collection<Picture> getPicturesForRandomDay(final Locale locale) {
        return getPicturesForDate(locale, calculateRandomDate());
    }

    private String calculateRandomDate() {
        final var tomorrow = LocalDate.now().plusDays(1);
        final var randomDate = ThreadLocalRandom.current()
                .nextLong(ApiDateUtils.START_DATE.toEpochDay(), tomorrow.toEpochDay());
        return LocalDate.ofEpochDay(randomDate).format(ApiDateUtils.DATE_FORMAT);
    }
}
