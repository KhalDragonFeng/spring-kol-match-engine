-- Seed KOL Profiles (20 realistic KOLs across platforms)
INSERT INTO kol_profile (username, platform, followers, engagement, category, tags, price, location, score) VALUES
('fashion_queen_lily',   'weibo',     1850000, 4.2, 'fashion',    'luxury,streetwear,ootd',        15000, 'Shanghai', 92.5),
('tech_guru_zhang',      'bilibili',  3200000, 5.8, 'technology', 'reviews,unboxing,tutorials',    25000, 'Shenzhen', 95.0),
('foodie_chen',          'douyin',    980000,  6.1, 'food',       'recipes,restaurants,street-food', 8000, 'Chengdu',  88.3),
('travel_nomad_wang',    'xiaohongshu', 2100000, 3.9, 'travel',   'luxury-travel,photography,vlogs', 18000, 'Beijing',  90.1),
('fitness_coach_liu',    'douyin',    1500000, 7.2, 'fitness',    'workout,nutrition,wellness',     12000, 'Guangzhou', 91.7),
('beauty_star_zhao',     'xiaohongshu', 4500000, 4.5, 'beauty',   'skincare,makeup,tutorials',      35000, 'Shanghai', 96.2),
('gaming_pro_sun',       'bilibili',  5800000, 8.1, 'gaming',    'esports,reviews,streaming',      28000, 'Hangzhou',  97.5),
('lifestyle_mama_wu',    'weibo',     750000,  5.3, 'parenting',  'motherhood,family,education',    6000,  'Nanjing',  82.4),
('auto_expert_li',       'douyin',    2800000, 3.7, 'automotive', 'cars,reviews,test-drive',         22000, 'Beijing',  93.8),
('music_vibes_yang',     'bilibili',  1200000, 6.5, 'music',     'covers,original,production',     10000, 'Chengdu',  87.6),
('pet_lover_huang',      'douyin',    3100000, 9.2, 'pets',      'dogs,cats,cute,adoption',        15000, 'Wuhan',    94.1),
('startup_insider_qian', 'weibo',     680000,  4.8, 'business',  'startups,vc,entrepreneurship',   8500,  'Beijing',  85.2),
('diy_master_zheng',     'xiaohongshu', 920000, 7.0, 'diy',      'crafts,home-decor,upcycling',    7000,  'Suzhou',   86.9),
('sports_fan_zhou',      'weibo',     1600000, 3.4, 'sports',    'basketball,football,commentary',  11000, 'Shanghai', 89.0),
('book_worm_xu',         'bilibili',  450000,  8.5, 'education', 'books,reviews,knowledge',         4000,  'Wuhan',    80.5),
('fashion_street_jin',   'douyin',    2200000, 5.1, 'fashion',   'streetwear,sneakers,haul',        16000, 'Guangzhou', 91.3),
('cook_master_ding',     'xiaohongshu', 1800000, 6.8, 'food',    'baking,chinese-cuisine,vegan',    14000, 'Hangzhou',  90.7),
('skincare_doc_cao',     'weibo',     3500000, 4.0, 'beauty',    'dermatology,skincare,science',    30000, 'Beijing',  95.5),
('adventure_bro_tang',   'douyin',    1350000, 5.6, 'travel',    'hiking,extreme,camping',          10000, 'Kunming',  88.8),
('digital_art_ma',       'bilibili',  800000,  7.8, 'art',       'illustration,3d,animation',       9000,  'Shanghai', 84.3);

-- Seed Campaigns
INSERT INTO campaign (name, brand, target_category, target_tags, min_followers, max_budget, min_engagement, target_platform) VALUES
('Summer Skincare Launch',    'GlowUp Cosmetics',  'beauty',     'skincare,tutorials',       500000,  40000, 3.5, 'xiaohongshu'),
('New Phone Release',         'TechVista Mobile',   'technology', 'reviews,unboxing',         1000000, 30000, 4.0, 'bilibili'),
('Food Festival Promo',       'TasteHub',           'food',       'recipes,restaurants',      300000,  15000, 5.0, 'douyin'),
('Fitness App Campaign',      'FitNow',             'fitness',    'workout,nutrition',        500000,  20000, 5.0, 'douyin'),
('Luxury Travel Package',     'WanderElite',        'travel',     'luxury-travel,photography', 1000000, 25000, 3.0, 'xiaohongshu'),
('Gaming Headset Launch',     'SonicWave Audio',    'gaming',     'reviews,streaming',        2000000, 35000, 6.0, 'bilibili'),
('Streetwear Collection',     'UrbanEdge',          'fashion',    'streetwear,sneakers,haul', 800000,  20000, 4.0, 'douyin'),
('Parenting App Awareness',   'KidSmart',           'parenting',  'family,education',         200000,  10000, 4.5, 'weibo');
