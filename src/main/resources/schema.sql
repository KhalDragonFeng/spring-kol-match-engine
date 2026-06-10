-- KOL Profiles
CREATE TABLE IF NOT EXISTS kol_profile (
    id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(100) NOT NULL,
    platform    VARCHAR(50)  NOT NULL DEFAULT 'weibo',
    followers   INT          NOT NULL DEFAULT 0,
    engagement  DOUBLE       NOT NULL DEFAULT 0.0,
    category    VARCHAR(100) NOT NULL DEFAULT 'lifestyle',
    tags        VARCHAR(500) DEFAULT '',
    price       DOUBLE       NOT NULL DEFAULT 0.0,
    location    VARCHAR(100) DEFAULT '',
    score       DOUBLE       NOT NULL DEFAULT 0.0,
    status      TINYINT      NOT NULL DEFAULT 1,
    deleted     TINYINT      NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Campaigns
CREATE TABLE IF NOT EXISTS campaign (
    id              BIGINT       PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(200) NOT NULL,
    brand           VARCHAR(100) NOT NULL,
    target_category VARCHAR(100) NOT NULL DEFAULT 'lifestyle',
    target_tags     VARCHAR(500) DEFAULT '',
    min_followers   INT          NOT NULL DEFAULT 0,
    max_budget      DOUBLE       NOT NULL DEFAULT 0.0,
    min_engagement  DOUBLE       NOT NULL DEFAULT 0.0,
    target_platform VARCHAR(50)  DEFAULT 'weibo',
    status          VARCHAR(20)  NOT NULL DEFAULT 'active',
    deleted         TINYINT      NOT NULL DEFAULT 0,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Match Results
CREATE TABLE IF NOT EXISTS match_result (
    id            BIGINT    PRIMARY KEY AUTO_INCREMENT,
    campaign_id   BIGINT    NOT NULL,
    kol_id        BIGINT    NOT NULL,
    score         DOUBLE    NOT NULL DEFAULT 0.0,
    category_score DOUBLE   NOT NULL DEFAULT 0.0,
    follower_score DOUBLE   NOT NULL DEFAULT 0.0,
    engagement_score DOUBLE NOT NULL DEFAULT 0.0,
    budget_score  DOUBLE    NOT NULL DEFAULT 0.0,
    status        VARCHAR(20) NOT NULL DEFAULT 'pending',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
