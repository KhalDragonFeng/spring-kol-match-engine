package com.qifanfeng.matchengine.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("kol_profile")
public class KolProfile {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private String platform;
    private Integer followers;
    private Double engagement;
    private String category;
    private String tags;
    private Double price;
    private String location;
    private Double score;
    private Integer status;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
