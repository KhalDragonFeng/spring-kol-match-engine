package com.qifanfeng.matchengine.controller;

import com.qifanfeng.matchengine.model.dto.MatchRequest;
import com.qifanfeng.matchengine.model.dto.MatchResponse;
import com.qifanfeng.matchengine.model.vo.ApiResponse;
import com.qifanfeng.matchengine.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/match")
@RequiredArgsConstructor
@Tag(name = "Match Engine", description = "KOL-Campaign matching endpoints")
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    @Operation(summary = "Execute match", description = "Find the best KOLs for a campaign based on category, followers, engagement, and budget requirements")
    public ApiResponse<List<MatchResponse>> executeMatch(@Valid @RequestBody MatchRequest request) {
        List<MatchResponse> results = matchService.executeMatch(request);
        return ApiResponse.success(
                String.format("Found %d matching KOLs", results.size()),
                results
        );
    }

    @PostMapping("/quick")
    @Operation(summary = "Quick match by category", description = "Simple match by category name only")
    public ApiResponse<List<MatchResponse>> quickMatch(
            @RequestParam String category,
            @RequestParam(defaultValue = "10") Integer limit) {
        MatchRequest request = new MatchRequest();
        request.setTargetCategory(category);
        request.setLimit(limit);
        List<MatchResponse> results = matchService.executeMatch(request);
        return ApiResponse.success(results);
    }
}
