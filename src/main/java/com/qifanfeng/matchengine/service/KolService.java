package com.qifanfeng.matchengine.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qifanfeng.matchengine.mapper.KolProfileMapper;
import com.qifanfeng.matchengine.model.entity.KolProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KolService {

    private final KolProfileMapper kolProfileMapper;

    public Page<KolProfile> listKols(int page, int size, String platform, String category) {
        LambdaQueryWrapper<KolProfile> query = new LambdaQueryWrapper<>();
        query.eq(KolProfile::getStatus, 1);

        if (platform != null && !platform.isBlank()) {
            query.eq(KolProfile::getPlatform, platform);
        }
        if (category != null && !category.isBlank()) {
            query.eq(KolProfile::getCategory, category);
        }

        query.orderByDesc(KolProfile::getScore);
        return kolProfileMapper.selectPage(new Page<>(page, size), query);
    }

    public KolProfile getById(Long id) {
        return kolProfileMapper.selectById(id);
    }

    public void create(KolProfile kol) {
        kolProfileMapper.insert(kol);
    }

    public void update(KolProfile kol) {
        kolProfileMapper.updateById(kol);
    }

    public void delete(Long id) {
        kolProfileMapper.deleteById(id);
    }
}
