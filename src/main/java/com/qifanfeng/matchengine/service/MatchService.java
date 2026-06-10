package com.qifanfeng.matchengine.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qifanfeng.matchengine.engine.CosineSimilarityEngine;
import com.qifanfeng.matchengine.mapper.KolProfileMapper;
import com.qifanfeng.matchengine.mapper.MatchResultMapper;
import com.qifanfeng.matchengine.model.dto.MatchRequest;
import com.qifanfeng.matchengine.model.dto.MatchResponse;
import com.qifanfeng.matchengine.model.entity.KolProfile;
import com.qifanfeng.matchengine.model.entity.MatchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final KolProfileMapper kolProfileMapper;
    private final MatchResultMapper matchResultMapper;
    private final CosineSimilarityEngine matchEngine;
    private final CacheService cacheService;

    /**
     * Execute a match: find the best KOLs for a given campaign request.
     */
    public List<MatchResponse> executeMatch(MatchRequest request) {
        log.info("Executing match for category={}, platform={}, minFollowers={}",
                request.getTargetCategory(), request.getTargetPlatform(), request.getMinFollowers());

        long start = System.currentTimeMillis();

        // 1. Try cache first
        String cacheKey = buildCacheKey(request);
        List<MatchResponse> cached = cacheService.getMatchResults(cacheKey);
        if (cached != null) {
            log.info("Cache HIT for key={}, returning {} results in {}ms",
                    cacheKey, cached.size(), System.currentTimeMillis() - start);
            return cached;
        }

        // 2. Query candidate KOLs with basic filters
        LambdaQueryWrapper<KolProfile> query = new LambdaQueryWrapper<>();
        query.eq(KolProfile::getStatus, 1);

        if (request.getTargetPlatform() != null && !request.getTargetPlatform().isBlank()) {
            query.eq(KolProfile::getPlatform, request.getTargetPlatform());
        }
        if (request.getMinFollowers() != null && request.getMinFollowers() > 0) {
            query.ge(KolProfile::getFollowers, request.getMinFollowers() / 2); // Broaden initial filter
        }

        List<KolProfile> candidates = kolProfileMapper.selectList(query);
        log.info("Found {} candidate KOLs after pre-filter", candidates.size());

        // 3. Score and rank using cosine similarity engine
        List<MatchResponse> results = matchEngine.match(request, candidates);

        // 4. Persist top results
        results.forEach(r -> {
            MatchResult record = new MatchResult();
            record.setKolId(r.getKolId());
            record.setScore(r.getTotalScore());
            record.setCategoryScore(r.getCategoryScore());
            record.setFollowerScore(r.getFollowerScore());
            record.setEngagementScore(r.getEngagementScore());
            record.setBudgetScore(r.getBudgetScore());
            record.setStatus("matched");
            matchResultMapper.insert(record);
        });

        // 5. Cache results
        cacheService.setMatchResults(cacheKey, results);

        log.info("Match completed: {} results in {}ms", results.size(), System.currentTimeMillis() - start);
        return results;
    }

    /**
     * Get match results by campaign ID.
     */
    public List<MatchResult> getResultsByCampaign(Long campaignId) {
        LambdaQueryWrapper<MatchResult> query = new LambdaQueryWrapper<>();
        query.eq(MatchResult::getCampaignId, campaignId)
                .orderByDesc(MatchResult::getScore);
        return matchResultMapper.selectList(query);
    }

    private String buildCacheKey(MatchRequest req) {
        return String.format("match:%s:%s:%d:%d:%.1f",
                req.getTargetCategory(),
                req.getTargetPlatform(),
                req.getMinFollowers(),
                req.getMaxBudget() != null ? req.getMaxBudget().intValue() : 0,
                req.getMinEngagement());
    }
}
