package org.nahuelrodriguez.miniaturecalendarapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheService {
    private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);
    private final CacheManager cacheManager;

    CacheService(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRateString = "${cache.time-to-live-in-millis}", initialDelay = 10000)
    public void evictAllCaches() {
        LOG.debug("Evicting cache...");
        cacheManager.getCacheNames()
                .stream()
                .map(cacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
    }
}
