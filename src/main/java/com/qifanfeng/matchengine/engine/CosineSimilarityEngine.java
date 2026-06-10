package com.qifanfeng.matchengine.engine;

import com.qifanfeng.matchengine.model.dto.MatchRequest;
import com.qifanfeng.matchengine.model.dto.MatchResponse;
import com.qifanfeng.matchengine.model.entity.KolProfile;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cosine-similarity based KOL matching engine.
 * <p>
 * Scores each KOL against a campaign's requirements across 4 dimensions:
 * 1. Category Match (weight: 0.35) — exact/partial category and tag overlap
 * 2. Follower Score (weight: 0.20) — normalized follower count vs. requirement
 * 3. Engagement Score (weight: 0.25) — engagement rate relative to threshold
 * 4. Budget Score (weight: 0.20) — price fit within budget constraints
 * <p>
 * Final score = weighted cosine similarity in 4D feature space.
 */
@Component
public class CosineSimilarityEngine {

    private static final double W_CATEGORY = 0.35;
    private static final double W_FOLLOWERS = 0.20;
    private static final double W_ENGAGEMENT = 0.25;
    private static final double W_BUDGET = 0.20;

    public List<MatchResponse> match(MatchRequest request, List<KolProfile> candidates) {
        return candidates.stream()
                .map(kol -> scoreKol(request, kol))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(MatchResponse::getTotalScore).reversed())
                .limit(request.getLimit())
                .collect(Collectors.toList());
    }

    private MatchResponse scoreKol(MatchRequest request, KolProfile kol) {
        double catScore = calculateCategoryScore(request, kol);
        double folScore = calculateFollowerScore(request, kol);
        double engScore = calculateEngagementScore(request, kol);
        double budScore = calculateBudgetScore(request, kol);

        // Cosine similarity in weighted 4D space
        double[] reqVector = { W_CATEGORY, W_FOLLOWERS, W_ENGAGEMENT, W_BUDGET };
        double[] kolVector = {
                W_CATEGORY * catScore,
                W_FOLLOWERS * folScore,
                W_ENGAGEMENT * engScore,
                W_BUDGET * budScore
        };

        double dotProduct = 0, normReq = 0, normKol = 0;
        for (int i = 0; i < 4; i++) {
            dotProduct += reqVector[i] * kolVector[i];
            normReq += reqVector[i] * reqVector[i];
            normKol += kolVector[i] * kolVector[i];
        }

        double totalScore = (normReq > 0 && normKol > 0)
                ? dotProduct / (Math.sqrt(normReq) * Math.sqrt(normKol))
                : 0.0;

        return MatchResponse.fromKolWithScore(kol, totalScore, catScore, folScore, engScore, budScore);
    }

    /**
     * Category match: 1.0 for exact match, partial credit for tag overlap.
     */
    private double calculateCategoryScore(MatchRequest req, KolProfile kol) {
        double score = 0.0;

        // Exact category match
        if (req.getTargetCategory() != null
                && req.getTargetCategory().equalsIgnoreCase(kol.getCategory())) {
            score += 0.6;
        }

        // Tag overlap (Jaccard coefficient)
        if (req.getTargetTags() != null && kol.getTags() != null) {
            Set<String> reqTags = new HashSet<>(Arrays.asList(req.getTargetTags().toLowerCase().split(",")));
            Set<String> kolTags = new HashSet<>(Arrays.asList(kol.getTags().toLowerCase().split(",")));
            reqTags.replaceAll(String::trim);
            kolTags.replaceAll(String::trim);

            Set<String> intersection = new HashSet<>(reqTags);
            intersection.retainAll(kolTags);
            Set<String> union = new HashSet<>(reqTags);
            union.addAll(kolTags);

            if (!union.isEmpty()) {
                score += 0.4 * ((double) intersection.size() / union.size());
            }
        }

        return Math.min(score, 1.0);
    }

    /**
     * Follower score: sigmoid curve centered around the minimum requirement.
     */
    private double calculateFollowerScore(MatchRequest req, KolProfile kol) {
        if (req.getMinFollowers() == null || req.getMinFollowers() <= 0) return 0.8;
        double ratio = (double) kol.getFollowers() / req.getMinFollowers();
        // Sigmoid: 1 / (1 + e^(-5*(ratio-1)))
        return 1.0 / (1.0 + Math.exp(-5.0 * (ratio - 1.0)));
    }

    /**
     * Engagement score: normalized against the minimum engagement threshold.
     */
    private double calculateEngagementScore(MatchRequest req, KolProfile kol) {
        if (req.getMinEngagement() == null || req.getMinEngagement() <= 0) return 0.7;
        double ratio = kol.getEngagement() / req.getMinEngagement();
        return Math.min(ratio / 2.0, 1.0); // Cap at 1.0, 2x the requirement = perfect score
    }

    /**
     * Budget score: how well the KOL's price fits the campaign's budget.
     * Perfect score when price is 40-70% of max budget.
     */
    private double calculateBudgetScore(MatchRequest req, KolProfile kol) {
        if (req.getMaxBudget() == null || req.getMaxBudget() <= 0) return 0.5;
        if (kol.getPrice() > req.getMaxBudget()) return 0.0;

        double ratio = kol.getPrice() / req.getMaxBudget();
        if (ratio >= 0.4 && ratio <= 0.7) return 1.0;       // Sweet spot
        if (ratio < 0.4) return 0.5 + (ratio / 0.4) * 0.5;  // Too cheap might mean low quality
        return 1.0 - ((ratio - 0.7) / 0.3) * 0.5;           // Getting expensive
    }
}
