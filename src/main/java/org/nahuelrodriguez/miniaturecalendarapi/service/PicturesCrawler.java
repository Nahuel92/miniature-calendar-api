package org.nahuelrodriguez.miniaturecalendarapi.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.nahuelrodriguez.miniaturecalendarapi.entity.Picture;
import org.nahuelrodriguez.miniaturecalendarapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.net.URL;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class PicturesCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(PicturesCrawler.class);
    private final String endpoint;

    PicturesCrawler(@Value("${image.source.api.url}") final String endpoint) {
        Objects.requireNonNull(endpoint);
        if (endpoint.isBlank()) {
            throw new IllegalArgumentException("The API URL cannot be blank!");
        }
        LOG.info("Using '{}' as image source API URL", endpoint);
        this.endpoint = endpoint;
    }

    public Collection<Picture> getPicturesForDate(final String date) {
        final var document = getDocument(date);
        final var pictureURLs = getPictureURIs(document);
        return createPictures(pictureURLs);
    }

    private Document getDocument(final String date) {
        LOG.info("Fetching webpage for date '{}'...", date);
        return Utils.tryAndGetOrThrow(() -> Jsoup.connect(endpoint + date).get());
    }

    private List<String> getPictureURIs(final Document document) {
        LOG.debug("Extracting picture links from webpage...");
        return document.select("img[src$=.jpg]")
                .not(".wp-post-image")
                .eachAttr("src");
    }

    private Collection<Picture> createPictures(final Collection<String> pictureUrl) {
        LOG.debug("Downloading pictures...");
        return pictureUrl.parallelStream()
                .map(this::create)
                .map(e -> new Picture(e, create(e)))
                .toList();
    }

    private URL create(final String pictureUrl) {
        return Utils.tryAndGetOrThrow(() -> new URL(pictureUrl));
    }

    private String create(final URL pictureUrl) {
        return Utils.tryAndGetOrThrow(() -> Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(pictureUrl.openStream())));
    }
}
