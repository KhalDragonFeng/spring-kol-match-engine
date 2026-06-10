package com.qifanfeng.matchengine.engine;

import com.qifanfeng.matchengine.model.dto.MatchRequest;
import com.qifanfeng.matchengine.model.dto.MatchResponse;
import com.qifanfeng.matchengine.model.entity.KolProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CosineSimilarityEngineTest {

    private CosineSimilarityEngine engine;

    @BeforeEach
    void setUp() {
        engine = new CosineSimilarityEngine();
    }

    @Test
    void shouldRankExactCategoryMatchHigher() {
        KolProfile fashionKol = createKol(1L, "fashion_queen", "douyin", 1000000, 5.0, "fashion", "luxury,ootd", 10000);
        KolProfile techKol = createKol(2L, "tech_guru", "douyin", 1000000, 5.0, "technology", "reviews", 10000);

        MatchRequest request = new MatchRequest();
        request.setTargetCategory("fashion");
        request.setTargetTags("luxury");
        request.setMinFollowers(500000);
        request.setMaxBudget(20000.0);
        request.setMinEngagement(3.0);
        request.setLimit(10);

        List<MatchResponse> results = engine.match(request, List.of(fashionKol, techKol));

        assertEquals(2, results.size());
        assertEquals("fashion_queen", results.get(0).getUsername());
        assertTrue(results.get(0).getTotalScore() > results.get(1).getTotalScore());
    }

    @Test
    void shouldFilterByBudget() {
        KolProfile cheapKol = createKol(1L, "cheap", "douyin", 500000, 5.0, "beauty", "skincare", 5000);
        KolProfile expensiveKol = createKol(2L, "expensive", "douyin", 5000000, 5.0, "beauty", "skincare", 100000);

        MatchRequest request = new MatchRequest();
        request.setTargetCategory("beauty");
        request.setMaxBudget(10000.0);
        request.setMinEngagement(3.0);
        request.setLimit(10);

        List<MatchResponse> results = engine.match(request, List.of(cheapKol, expensiveKol));

        assertTrue(results.get(0).getBudgetScore() > results.get(1).getBudgetScore());
    }

    @Test
    void shouldReturnGrades() {
        KolProfile kol = createKol(1L, "perfect_match", "douyin", 2000000, 8.0, "beauty", "skincare,makeup,tutorials", 15000);

        MatchRequest request = new MatchRequest();
        request.setTargetCategory("beauty");
        request.setTargetTags("skincare,tutorials");
        request.setMinFollowers(1000000);
        request.setMaxBudget(30000.0);
        request.setMinEngagement(4.0);
        request.setLimit(10);

        List<MatchResponse> results = engine.match(request, List.of(kol));

        assertEquals(1, results.size());
        assertNotNull(results.get(0).getMatchGrade());
        assertTrue(List.of("S", "A", "B", "C").contains(results.get(0).getMatchGrade()));
    }

    private KolProfile createKol(Long id, String username, String platform, int followers,
                                  double engagement, String category, String tags, double price) {
        KolProfile kol = new KolProfile();
        kol.setId(id);
        kol.setUsername(username);
        kol.setPlatform(platform);
        kol.setFollowers(followers);
        kol.setEngagement(engagement);
        kol.setCategory(category);
        kol.setTags(tags);
        kol.setPrice(price);
        kol.setLocation("Test City");
        kol.setScore(85.0);
        kol.setStatus(1);
        return kol;
    }
}
