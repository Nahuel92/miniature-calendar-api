package org.nahuelrodriguez.miniaturecalendarapi.controller;

import org.nahuelrodriguez.miniaturecalendarapi.entity.Picture;
import org.nahuelrodriguez.miniaturecalendarapi.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class Controller {
    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
    private final ApiService apiService;

    Controller(final ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/pictures")
    public Collection<Picture> getPicturesOfRandomDay(final Locale locale, @RequestParam boolean random) {
        if (random) {
            LOG.info("Retrieving pictures of random day");
            return apiService.getPicturesFromRandomDay(locale);
        }
        LOG.info("Retrieving pictures of the day");
        return apiService.getPicturesOfTheDay(locale);
    }

    @GetMapping("/pictures/{date}")
    public Collection<Picture> getPictureFromDate(final Locale locale, @PathVariable final String date) {
        LOG.info("Retrieving picture from date '{}'", date);
        return apiService.getPicturesFromDate(locale, date);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map.Entry<String, String> handle(final IllegalArgumentException e) {
        LOG.warn("Bad request: '{}'", e.getMessage());
        return Map.entry("error", e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map.Entry<String, String> handle(final RuntimeException e) {
        LOG.error("Error: '{}'", e.getMessage());
        return Map.entry("error", "Service unavailable. Try again later.");
    }
}
