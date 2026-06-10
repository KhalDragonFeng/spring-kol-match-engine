package com.qifanfeng.matchengine.controller;

import com.qifanfeng.matchengine.model.vo.ApiResponse;
import com.qifanfeng.matchengine.service.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "System", description = "Health check and system endpoints")
public class HealthController {

    private final CacheService cacheService;

    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.success(Map.of(
                "status", "UP",
                "service", "kol-match-engine",
                "version", "1.0.0",
                "cacheEntries", cacheService.getCacheSize()
        ));
    }
}
