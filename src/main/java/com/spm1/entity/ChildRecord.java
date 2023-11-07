package com.spm1.entity;

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
    private String childId;
    private String donorId;
    private LocalDateTime donateDate;
    private Double money;
}
