package com.qifanfeng.matchengine.model.dto;

import com.qifanfeng.matchengine.model.entity.KolProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {

    private Long kolId;
    private String username;
    private String platform;
    private Integer followers;
    private Double engagement;
    private String category;
    private String tags;
    private Double price;
    private String location;

    // Scoring breakdown
    private Double totalScore;
    private Double categoryScore;
    private Double followerScore;
    private Double engagementScore;
    private Double budgetScore;
    private String matchGrade; // S, A, B, C

    public static MatchResponse fromKolWithScore(KolProfile kol, Double totalScore,
            Double categoryScore, Double followerScore, Double engagementScore, Double budgetScore) {
        String grade;
        if (totalScore >= 0.85) grade = "S";
        else if (totalScore >= 0.70) grade = "A";
        else if (totalScore >= 0.50) grade = "B";
        else grade = "C";

        return MatchResponse.builder()
                .kolId(kol.getId())
                .username(kol.getUsername())
                .platform(kol.getPlatform())
                .followers(kol.getFollowers())
                .engagement(kol.getEngagement())
                .category(kol.getCategory())
                .tags(kol.getTags())
                .price(kol.getPrice())
                .location(kol.getLocation())
                .totalScore(Math.round(totalScore * 10000.0) / 10000.0)
                .categoryScore(Math.round(categoryScore * 10000.0) / 10000.0)
                .followerScore(Math.round(followerScore * 10000.0) / 10000.0)
                .engagementScore(Math.round(engagementScore * 10000.0) / 10000.0)
                .budgetScore(Math.round(budgetScore * 10000.0) / 10000.0)
                .matchGrade(grade)
                .build();
    }
}
