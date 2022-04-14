package org.nahuelrodriguez.miniaturecalendarapi;

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
    private final RandomDateGenerator randomDateGenerator;

    Controller(final ApiService apiService, final RandomDateGenerator randomDateGenerator) {
        this.apiService = apiService;
        this.randomDateGenerator = randomDateGenerator;
    }

    @GetMapping("/picture")
    public Collection<Picture> getPictureOfTheDay(final Locale locale, @RequestParam final Boolean random) {
        LOG.info("Retrieving " + (random ? "random picture" : "image of the day"));
        if (random) {
            return apiService.getPicturesFromDate(locale, randomDateGenerator.getDate());
        }
        return apiService.getPicturesOfTheDay(locale);
    }

    @GetMapping("/picture/{date}")
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
        return Map.entry("error", "Internal server error");
    }
}
