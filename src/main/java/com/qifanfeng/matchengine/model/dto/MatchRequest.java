package com.qifanfeng.matchengine.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MatchRequest {

    @NotBlank(message = "Target category is required")
    private String targetCategory;

    private String targetTags;

    @Min(value = 0, message = "Min followers must be >= 0")
    private Integer minFollowers = 0;

    @Min(value = 0, message = "Max budget must be >= 0")
    private Double maxBudget = 100000.0;

    @Min(value = 0, message = "Min engagement must be >= 0")
    private Double minEngagement = 0.0;

    private String targetPlatform;

    @Min(value = 1, message = "Limit must be >= 1")
    private Integer limit = 20;
}
