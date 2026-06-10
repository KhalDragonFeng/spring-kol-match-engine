package com.qifanfeng.matchengine.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("campaign")
public class Campaign {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String brand;
    private String targetCategory;
    private String targetTags;
    private Integer minFollowers;
    private Double maxBudget;
    private Double minEngagement;
    private String targetPlatform;
    private String status;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
