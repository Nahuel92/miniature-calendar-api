package org.nahuelrodriguez.miniaturecalendarapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@Service
public class ApiService {
    private static final Logger LOG = LoggerFactory.getLogger(ApiService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");
    private final String endpoint;

    ApiService(@Value("${image.source.api.url}") final String endpoint) {
        Objects.requireNonNull(endpoint);
        if (endpoint.isBlank()) {
            throw new IllegalArgumentException("The API URL cannot be blank!");
        }
        LOG.info("Using '{}' as image source API URL", endpoint);
        this.endpoint = endpoint;
    }

    @Cacheable("picturesOfTheDay")
    public Collection<Picture> getPicturesOfTheDay() {
        final var today = LocalDate.now().format(DATE_FORMAT);
        return getPicturesFromDate(today);
    }

    @Cacheable(value = "picturesFromDate", key = "#date", unless = "#result == null")
    public Collection<Picture> getPicturesFromDate(final String date) {
        validateFormat(date);
        final var document = getDocument(date);
        final var pictureURLs = getPictureURLs(document);
        return createPictures(pictureURLs);
    }

    private void validateFormat(final String date) {
        LOG.info("Validating date");
        resultOf(() -> LocalDate.parse(date, DATE_FORMAT));
    }

    private Document getDocument(final String today) {
        LOG.info("Fetching webpage...");
        return resultOf(() -> Jsoup.connect(endpoint + today).get());
    }

    private List<String> getPictureURLs(final Document document) {
        LOG.info("Extracting picture links from webpage...");
        return document.select("img[src$=.jpg]")
                .not(".wp-post-image")
                .eachAttr("src");
    }

    private Collection<Picture> createPictures(final Collection<String> pictureUrl) {
        LOG.info("Downloading pictures...");
        return pictureUrl.parallelStream()
                .map(this::create)
                .map(e -> new Picture(e, create(e)))
                .toList();
    }

    private URL create(final String pictureUrl) {
        return resultOf(() -> new URL(pictureUrl));
    }

    private String create(final URL pictureUrl) {
        return resultOf(() -> Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(pictureUrl.openStream())));
    }

    private <T> T resultOf(final Callable<T> action) {
        try {
            return action.call();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
