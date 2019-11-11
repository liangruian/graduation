package com.lra.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/17 14:10
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
