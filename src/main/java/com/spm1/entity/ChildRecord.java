package com.spm1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("childRecords")
public class ChildRecord {
    @TableId
    private String id;
    @TableField("child_id")
    private String childId;
    @TableField("donor_id")
    private String donorId;
    @TableField("donate_date")
    private LocalDateTime donateDate;
    private Double money;
}
