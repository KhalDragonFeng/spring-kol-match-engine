package com.qifanfeng.matchengine.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("match_result")
public class MatchResult {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long campaignId;
    private Long kolId;
    private Double score;
    private Double categoryScore;
    private Double followerScore;
    private Double engagementScore;
    private Double budgetScore;
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
