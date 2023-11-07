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
@TableName("childItems")
public class ChildItem {

    @TableId
    private String id;
    private String title;
    private String childId;
    private String content;
    private String imageUrl;
    private LocalDateTime date;

}
