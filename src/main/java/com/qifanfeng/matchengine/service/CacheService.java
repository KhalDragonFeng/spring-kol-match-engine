package com.qifanfeng.matchengine.service;

import com.qifanfeng.matchengine.model.dto.MatchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache service with graceful Redis fallback.
 * Uses in-memory ConcurrentHashMap when Redis is unavailable.
 */
@Slf4j
@Service
public class CacheService {

    @Value("${app.redis.enabled:false}")
    private boolean redisEnabled;

    // In-memory fallback cache
    private final Map<String, CacheEntry<List<MatchResponse>>> localCache = new ConcurrentHashMap<>();

    private static final long DEFAULT_TTL_MS = 30 * 60 * 1000; // 30 minutes

    public List<MatchResponse> getMatchResults(String key) {
        CacheEntry<List<MatchResponse>> entry = localCache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }
        if (entry != null) {
            localCache.remove(key);
        }
        return null;
    }

    public void setMatchResults(String key, List<MatchResponse> results) {
        localCache.put(key, new CacheEntry<>(results, System.currentTimeMillis() + DEFAULT_TTL_MS));
        log.debug("Cached {} results for key={}", results.size(), key);
    }

    public void evict(String key) {
        localCache.remove(key);
    }

    public void clearAll() {
        localCache.clear();
        log.info("Cache cleared");
    }

    public int getCacheSize() {
        return localCache.size();
    }

    private static class CacheEntry<T> {
        private final T value;
        private final long expiresAt;

        CacheEntry(T value, long expiresAt) {
            this.value = value;
            this.expiresAt = expiresAt;
        }

        T getValue() { return value; }
        boolean isExpired() { return System.currentTimeMillis() > expiresAt; }
    }
}
