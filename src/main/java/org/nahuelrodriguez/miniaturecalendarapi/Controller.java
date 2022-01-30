package org.nahuelrodriguez.miniaturecalendarapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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
    public Collection<Picture> getPictureOfTheDay(@RequestParam final Boolean random) {
        LOG.info("Retrieving " + (random ? "random picture" : "image of the day"));
        if (random) {
            return apiService.getPicturesFromDate(randomDateGenerator.getDate());
        }
        return apiService.getPicturesOfTheDay();
    }

    @GetMapping("/picture/{date}")
    public Collection<Picture> getPictureFromDate(@PathVariable final String date) {
        LOG.info("Retrieving picture from date '{}'", date);
        return apiService.getPicturesFromDate(date);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map.Entry<String, String> handle(final RuntimeException e) {
        LOG.error("Error: '{}'", e.getMessage());
        return Map.entry("error", e.getMessage());
    }
}
