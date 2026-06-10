package com.qifanfeng.matchengine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qifanfeng.matchengine.model.entity.KolProfile;
import com.qifanfeng.matchengine.model.vo.ApiResponse;
import com.qifanfeng.matchengine.service.KolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kols")
@RequiredArgsConstructor
@Tag(name = "KOL Management", description = "CRUD operations for KOL profiles")
public class KolController {

    private final KolService kolService;

    @GetMapping
    @Operation(summary = "List KOLs", description = "Paginated list with optional platform and category filters")
    public ApiResponse<Page<KolProfile>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) String category) {
        return ApiResponse.success(kolService.listKols(page, size, platform, category));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get KOL by ID")
    public ApiResponse<KolProfile> getById(@PathVariable Long id) {
        KolProfile kol = kolService.getById(id);
        if (kol == null) {
            return ApiResponse.error(404, "KOL not found");
        }
        return ApiResponse.success(kol);
    }

    @PostMapping
    @Operation(summary = "Create KOL profile")
    public ApiResponse<String> create(@RequestBody KolProfile kol) {
        kolService.create(kol);
        return ApiResponse.success("KOL created", kol.getId().toString());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update KOL profile")
    public ApiResponse<String> update(@PathVariable Long id, @RequestBody KolProfile kol) {
        kol.setId(id);
        kolService.update(kol);
        return ApiResponse.success("KOL updated", id.toString());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete KOL profile (soft delete)")
    public ApiResponse<String> delete(@PathVariable Long id) {
        kolService.delete(id);
        return ApiResponse.success("KOL deleted", id.toString());
    }
}
